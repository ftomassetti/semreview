package it.polito.semreview.resourcelookup;

import it.polito.semreview.keyphrasesextraction.KeyPhrase;

public interface ResourceRetriever {

	String getDefinitionText(KeyPhrase keyPhrase);
	
}
