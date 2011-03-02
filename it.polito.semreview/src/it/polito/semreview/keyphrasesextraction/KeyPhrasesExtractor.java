package it.polito.semreview.keyphrasesextraction;

import it.polito.semreview.dataset.Paper;

import java.util.Set;

public interface KeyPhrasesExtractor {

	Set<KeyPhrase> getKeyPhrases(Paper paper); 
	
}
