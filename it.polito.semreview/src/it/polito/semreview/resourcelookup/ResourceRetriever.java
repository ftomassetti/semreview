package it.polito.semreview.resourcelookup;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;

import java.io.IOException;

public interface ResourceRetriever {

	String getDefinitionText(KeyPhrase keyPhrase) throws IOException;
	
}
