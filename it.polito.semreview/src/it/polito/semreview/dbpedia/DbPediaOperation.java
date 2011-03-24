package it.polito.semreview.dbpedia;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;

public class DbPediaOperation implements Operation<String> {
	
	private KeyPhrase keyPhrase;
	
	public DbPediaOperation(KeyPhrase keyPhrase){
		this.keyPhrase = keyPhrase;
	}

	@Override
	public String execute() throws NoResourceFoundException, ParserConfigurationException, IOException, UnvalidResponseException, UnvalidDefinitionException {
		DbPediaFacade dbPedia = new DbPediaFacadeImpl();
		return dbPedia.retrieveAbstract(keyPhrase.text());
	}

}
