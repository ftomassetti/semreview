package it.polito.semreview.enrichment.keyphrasesextraction;

import it.polito.semreview.dataset.paper.Paper;
import it.polito.softeng.common.collections.Pair;

import java.util.HashSet;

public class TextToPaperKeyPhrasesExtractorAdapter implements KeyPhrasesExtractor<Paper> {
	
	private KeyPhrasesExtractor<String> textKeyPhrasesExtractor;

	public TextToPaperKeyPhrasesExtractorAdapter(
			KeyPhrasesExtractor<String> textKeyPhrasesExtractor) {
		super();
		this.textKeyPhrasesExtractor = textKeyPhrasesExtractor;
	}

	@Override
	public HashSet<Pair<KeyPhrase, Double>> getKeyPhrases(Paper element) {
		return textKeyPhrasesExtractor.getKeyPhrases(element.collateText());
	}

}
