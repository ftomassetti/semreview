package it.polito.semreview.dbpedia;

import com.google.common.base.Preconditions;
import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Operation which retrieve the abstract for the given KeyPhrase.
 */
public class DbPediaOperation implements Operation<String> {
	
	private final KeyPhrase keyPhrase;
	
	public DbPediaOperation(@NotNull KeyPhrase keyPhrase){
        Preconditions.checkNotNull(keyPhrase, "KeyPhrase is expected to be not null");
		this.keyPhrase = keyPhrase;
	}

	@Override
	public String execute() throws NoResourceFoundException, ParserConfigurationException, IOException, InvalidResponseException, InvalidDefinitionException {
		DbPediaFacade dbPedia = new DbPediaFacadeImpl();
		return dbPedia.retrieveAbstract(keyPhrase.text());
	}

}
