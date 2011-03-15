package it.polito.semreview.enrichment.keyphrasesextraction;

import it.polito.softeng.common.Pair;

import java.util.HashSet;

public interface KeyPhrasesExtractor<E> {
	
	/**
	 * Explicit type returned just because Set is not Serializable while HashSet is...
	 * @param element
	 * @return
	 */
	HashSet<Pair<KeyPhrase,Double>> getKeyPhrases(E element); 
}
