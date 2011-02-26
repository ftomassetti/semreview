package it.polito.semreview.opencalais;

import it.polito.semreview.utils.Pair;

import java.util.List;

public interface KeyPhrasesProvider {

	List<Pair<String, Double>> calculateKeyPhrases(String text);

}
