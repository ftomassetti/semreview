package it.polito.semreview.enrichment.namedentityextraction.spotlight;

import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.semreview.enrichment.namedentityextraction.NamedEntitiesExtractor;
import it.polito.semreview.enrichment.namedentityextraction.NamedEntity;
import it.polito.semreview.enrichment.namedentityextraction.NamedEntityImpl;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SpotlightNamedEntityProvider 
				implements NamedEntitiesExtractor<String> {
	
	private static Logger logger = Logger
	.getLogger(OpenCalaisKeyPhrasesProvider.class);

	@Override
	public HashSet<NamedEntity> getNamedEntities(String text) {
		
		HashSet<NamedEntity> namedEntities = new HashSet<NamedEntity>();
		String xml;
		try {
			 xml = Spotlight.run(text, 0.5, 20);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		
		if (xml != null) {
			logger.debug(xml);
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(xml));
				Document doc = db.parse(is);
				doc.getDocumentElement().normalize();
				logger.debug("Root element "
						+ doc.getDocumentElement().getNodeName());
				NodeList spotlightList = doc
						.getElementsByTagName("Annotation");
				logger.debug("---------");
				Node spotligthNode = spotlightList.item(0);
				Element spotlight = (Element) spotligthNode;
				NodeList resourcesList = spotlight
						.getElementsByTagName("Resources");
				if (resourcesList.getLength() == 0) {
					// No node SocialTags
				} else {
					Element resourcesRootElement = (Element) resourcesList.item(0);
					logger.debug("---------");
					logger.debug("Node Name : " + resourcesRootElement.getNodeName());
					NodeList resources = resourcesRootElement.getChildNodes();
					logger.debug("Resource "+resources.getLength());
					for (int s = 0; s < resources.getLength(); s++) {
						Node resource = resources.item(s);
						if (resource.getNodeType() == Node.ELEMENT_NODE) {
							NamedNodeMap attributes = resource.getAttributes();
							String name = attributes.getNamedItem("surfaceForm").getNodeValue();
							String uri = attributes.getNamedItem("URI").getNodeValue();
							String type = attributes.getNamedItem("types").getNodeValue();
							String similarityScore = attributes.
										getNamedItem("similarityScore").getNodeValue();
							String support = attributes.getNamedItem("support").getNodeValue();
							
							namedEntities.add(
										new NamedEntityImpl(name, type, uri, 
													similarityScore, support)
										);
						}		
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
