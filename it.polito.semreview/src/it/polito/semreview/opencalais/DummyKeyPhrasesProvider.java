package it.polito.semreview.opencalais;

import it.polito.softeng.common.Pair;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DummyKeyPhrasesProvider implements KeyPhrasesProvider {

	public List<Pair<String, Double>> calculateKeyPhrases(String text) {
		List<Pair<String, Double>> list = new LinkedList<Pair<String, Double>>();
		String xml = null;
		xml = OpenCalais.run(text);
		String keyword = null;
		String relevance = null;
		if (xml != null) {
			String xmlOk = "<?xml version=\"1.0\"?>\n" + xml;
			System.out.println(xmlOk);
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(xmlOk));
				Document doc = db.parse(is);
				doc.getDocumentElement().normalize();
				System.out.println("Root element "
						+ doc.getDocumentElement().getNodeName());
				NodeList openCalaisSimpleList = doc
						.getElementsByTagName("OpenCalaisSimple");
				System.out.println("---------");
				Node openCalaisSimpleNode = openCalaisSimpleList.item(0);
				Element openCalaisSimple = (Element) openCalaisSimpleNode;
				NodeList calaisSimpleOutputFormatList = openCalaisSimple
						.getElementsByTagName("CalaisSimpleOutputFormat");
				Element calaisSimpleOutputFormatElement = (Element) calaisSimpleOutputFormatList
						.item(0);
				System.out.println("Node Name : "
						+ calaisSimpleOutputFormatElement.getNodeName());
				NodeList SocialTagsList = calaisSimpleOutputFormatElement
						.getElementsByTagName("SocialTags");
				Element socialTags = (Element) SocialTagsList.item(0);
				System.out.println("---------");
				System.out.println("Node Name : " + socialTags.getNodeName());
				NodeList socialTagsNode = socialTags.getChildNodes();
				for (int s = 0; s < socialTagsNode.getLength(); s++) {
					Node fstNode = socialTagsNode.item(s);
					NodeList fstNm = fstNode.getChildNodes();
					Node socialtag = (Node) fstNm.item(0);
					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
						Node attr = fstNode.getAttributes().getNamedItem(
								"importance");
						keyword = socialtag.getNodeValue();
						relevance = attr.getNodeValue();
					}
					System.out.println("Keyword = " + keyword + " Relevance: "
							+ relevance);
					list.add(new Pair(keyword, Double.parseDouble(relevance)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
