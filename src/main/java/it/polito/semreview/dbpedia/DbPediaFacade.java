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
     * @throws InvalidResponseException
     * @throws InvalidDefinitionException
     */
	String retrieveAbstract(String keyphrase) throws NoResourceFoundException,
			ParserConfigurationException, IOException,
            InvalidResponseException, InvalidDefinitionException;

    /**
     * Retrieve the abstract associated to the given URI.
     * @param expectedURI
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     * @throws InvalidDefinitionException
     * @throws ParserConfigurationException
     */
	String retrieveAbstractFromURI(URI expectedURI) throws IOException, InvalidResponseException, InvalidDefinitionException, ParserConfigurationException;
}
