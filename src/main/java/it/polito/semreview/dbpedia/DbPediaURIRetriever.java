package it.polito.semreview.dbpedia;

import it.polito.softeng.common.collections.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DbPediaURIRetriever {

	public static final String DBPEDIA_RESOURCE_URI = "http://dbpedia.org/resource/";

	private Logger logger = Logger.getLogger(DbPediaURIRetriever.class);

	private DocumentParser documentParser = null;

	public DbPediaURIRetriever(DocumentParser documentParser) {
		this.documentParser = documentParser;
	}

	public static String getCommonlyExpectedURI(String keyPhrase) {
		return DBPEDIA_RESOURCE_URI + keyPhrase.replaceAll(" ", "_");
	}

	private static final String WS_URI = "http://lod.openlinksw.com/fct/service";
	private static String NEWLINE = System.getProperty("line.separator");

	private String createOpenLinkQueryDoc(String text) {
		StringBuilder doc = new StringBuilder();
		doc.append("<?xml version=\"1.0\"?>");
		doc.append("<query xmlns=\"http://openlinksw.com/services/facets/1.0\" inference=\"\" same-as=\"\">");
		doc.append("  <text>" + text + "</text>");
		doc.append("<view type=\"text\" limit=\"20\" offset=\"\"/>");
		doc.append("</query>");
		return doc.toString();
	}

	private Document getOpenLinkResponse(String queryDoc) throws IOException,
            InvalidResponseException {
		URL url = new URL(WS_URI);
		URLConnection conn = url.openConnection();
		logger.debug(".conn open");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml");
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(queryDoc);
		wr.flush();

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		logger.debug(".resp obtained");
		StringBuffer responseBuffer = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			// System.out.println(line);
			responseBuffer.append(line);
			responseBuffer.append(NEWLINE);
		}
		wr.close();
		rd.close();
		logger.debug(".done");
		try {
			return documentParser.parse(responseBuffer.toString());
		} catch (SAXException e) {
			throw new InvalidResponseException(
					"Response is not a valid XML file", e);
		}
	}

	private static final String RESPONSE_ROOT_NODE_NAME = "fct:facets";
	private static final String RESPONSE_FCT_RESULT_NODE_NAME = "fct:result";
	private static final String RESPONSE_FCT_ROW_NODE_NAME = "fct:row";
	private static final String RESPONSE_FCT_COLUMN_NODE_NAME = "fct:column";
	private static final String RESPONSE_DATATYPE_ATTR = "datatype";
	private static final String RESPONSE_DATATYPE_VALUE_URL = "url";
	private static final String RESPONSE_DATATYPE_VALUE_ERANK = "erank";

	private Pair<String, Double>[] getURIs(Document openLinkResponse)
			throws InvalidResponseException {
		List<Pair<String, Double>> uris = new LinkedList<Pair<String, Double>>();
		NodeList children = openLinkResponse.getChildNodes();
		if (children.getLength() != 1)
			throw new InvalidResponseException("One root expected");
		Node root = children.item(0);
		if (!root.getNodeName().equals(RESPONSE_ROOT_NODE_NAME))
			throw new InvalidResponseException("Root <"
					+ RESPONSE_ROOT_NODE_NAME + "> expected");

		children = root.getChildNodes();
		Node result = null;
		for (int ni = 0; ni < children.getLength(); ni++) {
			Node child = children.item(ni);
			if (child.getNodeName().equals(RESPONSE_FCT_RESULT_NODE_NAME)) {
				if (result != null) {
					throw new InvalidResponseException("More than one <"
							+ RESPONSE_FCT_RESULT_NODE_NAME + "> found");
				}
				result = child;
			}
		}
		if (result == null)
			throw new InvalidResponseException("No <"
					+ RESPONSE_FCT_RESULT_NODE_NAME + "> found");

		children = result.getChildNodes();
		List<Node> rows = new LinkedList<Node>();
		for (int ni = 0; ni < children.getLength(); ni++) {
			Node child = children.item(ni);
			if (child.getNodeName().equals(RESPONSE_FCT_ROW_NODE_NAME)) {
				rows.add(child);
			}
		}

		for (Node row : rows) {
			uris.add(new Pair<String, Double>(getUriFromRow(row),
					getRelevanceFromRow(row)));
		}

		return uris.toArray(new Pair[] {});
	}

	private String getUriFromRow(Node row) throws InvalidResponseException {
		NodeList children = row.getChildNodes();
		String result = null;
		for (int ni = 0; ni < children.getLength(); ni++) {
			Node column = children.item(ni);
			if (column.getNodeName().equals(RESPONSE_FCT_COLUMN_NODE_NAME)) {
				Node datatypeAttr = column.getAttributes().getNamedItem(
						RESPONSE_DATATYPE_ATTR);
				if (datatypeAttr != null
						&& datatypeAttr.getNodeValue().equals(
								RESPONSE_DATATYPE_VALUE_URL)) {
					if (result != null) {
						throw new InvalidResponseException(
								"A <"
										+ RESPONSE_FCT_ROW_NODE_NAME
										+ "> has more than one column with URL datatype");
					}
					result = column.getTextContent();

				}
			}
		}
		if (result == null)
			throw new InvalidResponseException("A <"
					+ RESPONSE_FCT_ROW_NODE_NAME
					+ "> has not a column with URL datatype");
		return result;
	}

	private Double getRelevanceFromRow(Node row)
			throws InvalidResponseException {
		NodeList children = row.getChildNodes();
		Double result = null;
		for (int ni = 0; ni < children.getLength(); ni++) {
			Node column = children.item(ni);
			if (column.getNodeName().equals(RESPONSE_FCT_COLUMN_NODE_NAME)) {
				Node datatypeAttr = column.getAttributes().getNamedItem(
						RESPONSE_DATATYPE_ATTR);
				if (datatypeAttr != null
						&& datatypeAttr.getNodeValue().equals(
								RESPONSE_DATATYPE_VALUE_ERANK)) {
					if (result != null) {
						throw new InvalidResponseException(
								"A <"
										+ RESPONSE_FCT_ROW_NODE_NAME
										+ "> has more than one column with erank datatype");
					}
					try {
						result = Double.parseDouble(column.getTextContent());
					} catch (NumberFormatException e) {
						throw new InvalidResponseException(
								"A <"
										+ RESPONSE_FCT_ROW_NODE_NAME
										+ "> has a column with erank datatype which have not a numerical value");
					}

				}
			}
		}
		if (result == null)
			throw new InvalidResponseException("A <"
					+ RESPONSE_FCT_ROW_NODE_NAME
					+ "> has not a column with erank datatype");
		return result;
	}

	public Pair<String, Double>[] retrievePossibileURIs(String text)
			throws IOException, InvalidResponseException {
		Document openLinkResponse = getOpenLinkResponse(createOpenLinkQueryDoc(text));
		return getURIs(openLinkResponse);
	}
}
