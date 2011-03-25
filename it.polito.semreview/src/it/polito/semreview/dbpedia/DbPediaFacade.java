package it.polito.semreview.dbpedia;

import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.ParserConfigurationException;

public interface DbPediaFacade {
	String retrieveAbstract(String keyphrase) throws NoResourceFoundException,
			ParserConfigurationException, IOException,
			UnvalidResponseException, UnvalidDefinitionException;
	String retrieveAbstractFromURI(URI expectedURI) throws IOException, UnvalidResponseException, UnvalidDefinitionException, ParserConfigurationException;
}
