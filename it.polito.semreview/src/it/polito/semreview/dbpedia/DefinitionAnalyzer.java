package it.polito.semreview.dbpedia;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class DefinitionAnalyzer {

	// prevent instantiation
	private DefinitionAnalyzer() {

	}

	private static final String RDF_ROOT = "rdf:RDF";
	private static final String RDF_ABOUT = "rdf:about";
	private static final String RDF_DESCRIPTION = "rdf:Description";
	private static final String DBPEDIA_OWL_ABSTRACT = "dbpedia-owl:abstract";
	private static final String DBPEDIA_OWL_SAMEAS = "owl:sameAs";
	private static final String XML_LANG = "xml:lang";
	private static final String LANG_ENGLISH = "en";

	public static String getAbstract(String resourceUri, Document definition)
			throws UnvalidDefinitionException {
		// System.out.println(" *** looking for uri "+resourceUri);
		if (definition == null)
			throw new IllegalArgumentException("Given definition is null");
		NodeList children = definition.getChildNodes();
		if (children.getLength() != 1)
			throw new UnvalidDefinitionException(definition,
					"One root expected");
		Node root = children.item(0);
		if (!root.getNodeName().equals(RDF_ROOT))
			throw new UnvalidDefinitionException(definition, "Root <"
					+ RDF_ROOT + "> expected");

		children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeName().equals(RDF_DESCRIPTION)) {
				Node rdfAboutVal = child.getAttributes()
						.getNamedItem(RDF_ABOUT);
				if (rdfAboutVal != null
						&& rdfAboutVal.getNodeValue().equals(resourceUri)) {
					NodeList descriptionChildren = child.getChildNodes();
					for (int dci = 0; dci < descriptionChildren.getLength(); dci++) {
						Node descriptionChild = descriptionChildren.item(dci);
						// System.out.println(">>> "+descriptionChild);
						if (descriptionChild.getNodeName().equals(
								DBPEDIA_OWL_SAMEAS)) {
							System.out.println("SAME AS "
									+ descriptionChild.getTextContent());
							NamedNodeMap m = descriptionChild.getAttributes();
							for (int j = 0; j < m.getLength(); j++) {
								System.out.println("\t" + m.item(j));
							}
						}
						if (descriptionChild.getNodeName().equals(
								DBPEDIA_OWL_ABSTRACT)) {
							Node langAttr = descriptionChild.getAttributes()
									.getNamedItem(XML_LANG);
							if (langAttr != null
									&& langAttr.getNodeValue().equals(
											LANG_ENGLISH)) {
								return descriptionChild.getTextContent();
							}
						}
					}
				}
			}
		}
		throw new UnvalidDefinitionException(definition,
				"No OWL_ABSTRACT available");
	}

}
