package it.polito.semreview.enrichment.keyphrasesextraction;

import it.polito.softeng.common.Pair;

import java.util.Set;

public interface KeyPhrasesExtractor<E> {
	Set<Pair<KeyPhrase,Double>> getKeyPhrases(E element); 
}
