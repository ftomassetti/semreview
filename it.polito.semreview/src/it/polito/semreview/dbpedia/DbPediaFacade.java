package it.polito.semreview.dbpedia;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public interface DbPediaFacade {
	String retrieveAbstract(String keyphrase) throws NoResourceFoundException,
			ParserConfigurationException, IOException,
			UnvalidResponseException, UnvalidDefinitionException;
}
