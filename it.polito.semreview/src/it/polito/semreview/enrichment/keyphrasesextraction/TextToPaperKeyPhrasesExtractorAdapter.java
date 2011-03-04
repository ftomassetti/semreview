package it.polito.semreview.enrichment.keyphrasesextraction;

import it.polito.semreview.dataset.Paper;
import it.polito.softeng.common.Pair;

import java.util.Set;

public class TextToPaperKeyPhrasesExtractorAdapter implements KeyPhrasesExtractor<Paper> {
	
	private KeyPhrasesExtractor<String> textKeyPhrasesExtractor;

	public TextToPaperKeyPhrasesExtractorAdapter(
			KeyPhrasesExtractor<String> textKeyPhrasesExtractor) {
		super();
		this.textKeyPhrasesExtractor = textKeyPhrasesExtractor;
	}

	@Override
	public Set<Pair<KeyPhrase, Double>> getKeyPhrases(Paper element) {
		return textKeyPhrasesExtractor.getKeyPhrases(element.collateText());
	}

}
