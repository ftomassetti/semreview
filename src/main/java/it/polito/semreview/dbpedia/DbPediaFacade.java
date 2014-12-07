package it.polito.semreview.dbpedia;

import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.ParserConfigurationException;

public interface DbPediaFacade {

    /**
     * Retrieve the abstract describing the given keyphrase.
     *
     * @param keyphrase
     * @return
     * @throws NoResourceFoundException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws UnvalidResponseException
     * @throws UnvalidDefinitionException
     */
	String retrieveAbstract(String keyphrase) throws NoResourceFoundException,
			ParserConfigurationException, IOException,
			UnvalidResponseException, UnvalidDefinitionException;

    /**
     * Retrieve the abstract associated to the given URI.
     * @param expectedURI
     * @return
     * @throws IOException
     * @throws UnvalidResponseException
     * @throws UnvalidDefinitionException
     * @throws ParserConfigurationException
     */
	String retrieveAbstractFromURI(URI expectedURI) throws IOException, UnvalidResponseException, UnvalidDefinitionException, ParserConfigurationException;
}
