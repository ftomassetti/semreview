package it.polito.semreview.dbpedia;

import it.polito.semreview.utils.ConsoleLogger;
import it.polito.semreview.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DefinitionRetriever {

	private Logger logger = new ConsoleLogger();
	private DocumentParser documentParser = null;

	public DefinitionRetriever(DocumentParser documentParser) {
		this.documentParser = documentParser;
	}

	private static String NEWLINE = System.getProperty("line.separator");

	public static boolean isADbPediaURI(String uri) {
		return uri.startsWith(RESOURCE_PREFIX);
	}

	private static String fromResourceToRawDataUri(String uri) {
		if (!isADbPediaURI(uri))
			throw new IllegalArgumentException("Not a DbPedia Resource URI");
		String rest = uri.substring(RESOURCE_PREFIX.length());
		return RAWDATA_PREFIX + rest + ".xml";
	}

	public Document retrieveDefinition(String uri) throws IOException,
			UnvalidResponseException {
		if (!isADbPediaURI(uri))
			throw new IllegalArgumentException("Not a DbPedia Resource URI");
		String rawDataUri = fromResourceToRawDataUri(uri);
		URL url = new URL(rawDataUri);
		URLConnection conn = url.openConnection();
		logger.log(".conn open");
		conn.setDoOutput(true);

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		logger.log(".resp obtained");
		StringBuffer responseBuffer = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			// System.out.println(line);
			responseBuffer.append(line);
			responseBuffer.append(NEWLINE);
		}
		rd.close();
		logger.log(".done");

		try {
			return documentParser.parse(responseBuffer.toString());
		} catch (SAXException e) {
			throw new UnvalidResponseException("Incorrect XML document", e);
		}
	}

	private static final String RESOURCE_PREFIX = "http://dbpedia.org/resource/";
	private static final String RAWDATA_PREFIX = "http://dbpedia.org/data/";

}
