package it.polito.semreview.dbpedia;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.resourcelookup.ResourceRetriever;
import it.polito.softeng.common.Pair;

import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class DbPediaFacadeImpl implements DbPediaFacade, ResourceRetriever {

	private Logger logger = Logger.getLogger(DbPediaFacadeImpl.class);

	@Override
	public String retrieveAbstractFromURI(URI expectedURI) throws IOException, UnvalidResponseException, UnvalidDefinitionException, ParserConfigurationException {
		DocumentParser documentParser = new DocumentParser();
		DefinitionRetriever definitionRetriever = new DefinitionRetriever(
				documentParser);
		Document definition = definitionRetriever
				.retrieveDefinition(expectedURI.toString());
		return DefinitionAnalyzer.getAbstract(expectedURI.toString(), definition);
	}

	@Override
	public String retrieveAbstract(String keyphrase)
			throws NoResourceFoundException, ParserConfigurationException,
			IOException, UnvalidResponseException, UnvalidDefinitionException {

		String expectedURI = DbPediaURIRetriever
				.getCommonlyExpectedURI(keyphrase);
		logger.debug("Expected URI for keyphrase '" + keyphrase + "' is '"
				+ expectedURI + "'");

		DocumentParser documentParser = new DocumentParser();
		DefinitionRetriever definitionRetriever = new DefinitionRetriever(
				documentParser);

		try {
			Document definition = definitionRetriever
					.retrieveDefinition(expectedURI);
			return DefinitionAnalyzer.getAbstract(expectedURI, definition);
		} catch (IOException e) {
			String maxURI = null;
			double maxErank = 0;
			DbPediaURIRetriever queryExecutor = new DbPediaURIRetriever(
					documentParser);
			Pair<String, Double>[] URIs = queryExecutor
					.retrievePossibileURIs(keyphrase);
			for (Pair<String, Double> uri : URIs) {
				System.out.println("URI " + uri);
				if (DefinitionRetriever.isADbPediaURI(uri.getFirst())) {
					double eRank = uri.getSecond();
					if (maxURI == null || eRank > maxErank) {
						maxURI = uri.getFirst();
						maxErank = eRank;
					}
				}
			}
			if (null == maxURI)
				throw new NoResourceFoundException(keyphrase);

			logger.debug("URI chosen for keyphrase '" + keyphrase + "' is '"
					+ maxURI + "'");

			Document definition = definitionRetriever
					.retrieveDefinition(maxURI);
			return DefinitionAnalyzer.getAbstract(maxURI, definition);
		}
	}

	@Override
	public String getDefinitionText(KeyPhrase keyPhrase) throws IOException,
			UnvalidDefinitionException {
		try {
			return retrieveAbstract(keyPhrase.text());
		} catch (NoResourceFoundException e) {
			throw new IOException(e);
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw new IOException(e);
		} catch (UnvalidResponseException e) {
			throw new IOException(e);
		}
	}

}
