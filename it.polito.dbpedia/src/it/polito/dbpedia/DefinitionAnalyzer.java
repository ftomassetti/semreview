package it.polito.dbpedia;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class DefinitionAnalyzer {

	// prevent instantiation
	private DefinitionAnalyzer(){
		
	}
	
	private static final String RDF_ROOT = "rdf:RDF";
	private static final String RDF_ABOUT = "rdf:about";
	private static final String RDF_DESCRIPTION = "rdf:Description";
	private static final String DBPEDIA_OWL_ABSTRACT = "dbpedia-owl:abstract";
	private static final String XML_LANG = "xml:lang";
	private static final String LANG_ENGLISH = "en";
	
	public static String getAbstract(String resourceUri, Document definition) throws UnvalidDefinitionException{		
		NodeList children = definition.getChildNodes();
		if (children.getLength()!=1) throw new UnvalidDefinitionException("One root expected");
		Node root = children.item(0);
		if (!root.getNodeName().equals(RDF_ROOT)) throw new UnvalidDefinitionException("Root <"+RDF_ROOT+"> expected");
		
		children = root.getChildNodes();
		for (int i=0;i<children.getLength();i++){
			Node child = children.item(i);
			if (child.getNodeName().equals(RDF_DESCRIPTION)){
				Node rdfAboutVal = child.getAttributes().getNamedItem(RDF_ABOUT);
				if (rdfAboutVal!=null && rdfAboutVal.getNodeValue().equals(resourceUri)){
					NodeList descriptionChildren = child.getChildNodes();
					for (int dci=0;dci<descriptionChildren.getLength();dci++){
						Node descriptionChild = descriptionChildren.item(dci);
						if (descriptionChild.getNodeName().equals(DBPEDIA_OWL_ABSTRACT)){
							Node langAttr = descriptionChild.getAttributes().getNamedItem(XML_LANG);
							if (langAttr!=null && langAttr.getNodeValue().equals(LANG_ENGLISH)){
								return descriptionChild.getTextContent();
							}
						}
					}
				}
			}
		}
		throw new UnvalidDefinitionException("No OWL_ABSTRACT available");
	}
	
}
