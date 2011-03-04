package it.polito.semreview.enrichment.keyphrasesextraction.opencalais;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhraseImpl;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.softeng.common.Pair;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OpenCalaisKeyPhrasesProvider implements KeyPhrasesExtractor<String> {
	
	private static Logger logger = Logger.getLogger(OpenCalaisKeyPhrasesProvider.class);

	public Set<Pair<KeyPhrase, Double>> getKeyPhrases(String text) {
		Set<Pair<KeyPhrase, Double>> keyPhrases = new HashSet<Pair<KeyPhrase, Double>>();
		String xml = null;
		xml = OpenCalais.run(text);
		String keyword = null;
		String relevance = null;
		if (xml != null) {
			String xmlOk = "<?xml version=\"1.0\"?>\n" + xml;
			logger.debug(xmlOk);
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(xmlOk));
				Document doc = db.parse(is);
				doc.getDocumentElement().normalize();
				logger.debug("Root element "
						+ doc.getDocumentElement().getNodeName());
				NodeList openCalaisSimpleList = doc
						.getElementsByTagName("OpenCalaisSimple");
				logger.debug("---------");
				Node openCalaisSimpleNode = openCalaisSimpleList.item(0);
				Element openCalaisSimple = (Element) openCalaisSimpleNode;
				NodeList calaisSimpleOutputFormatList = openCalaisSimple
						.getElementsByTagName("CalaisSimpleOutputFormat");
				Element calaisSimpleOutputFormatElement = (Element) calaisSimpleOutputFormatList
						.item(0);
				logger.debug("Node Name : "
						+ calaisSimpleOutputFormatElement.getNodeName());
				NodeList SocialTagsList = calaisSimpleOutputFormatElement
						.getElementsByTagName("SocialTags");
				Element socialTags = (Element) SocialTagsList.item(0);
				logger.debug("---------");
				logger.debug("Node Name : " + socialTags.getNodeName());
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
					logger.debug("Keyword = " + keyword + " Relevance: "
							+ relevance);
					keyPhrases.add(new Pair<KeyPhrase,Double>(new KeyPhraseImpl(keyword), Double.parseDouble(relevance)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return keyPhrases;
	}

}
