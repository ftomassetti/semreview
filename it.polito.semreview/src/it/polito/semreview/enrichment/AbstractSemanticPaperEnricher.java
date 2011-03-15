package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dbpedia.UnvalidDefinitionException;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.resourcelookup.ResourceRetriever;
import it.polito.softeng.common.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * To be extended by classes using keyphrases to enrich papers.
 */
public abstract class AbstractSemanticPaperEnricher implements PaperEnricher {

	private KeyPhrasesExtractor<Paper> keyPhrasesExtractor;
	private ResourceRetriever resourceRetriever;

	public AbstractSemanticPaperEnricher(
			KeyPhrasesExtractor<Paper> keyPhrasesExtractor,
			ResourceRetriever resourceRetriever) {
		this.keyPhrasesExtractor = keyPhrasesExtractor;
		this.resourceRetriever = resourceRetriever;
	}

	@Override
	public String getEnrichedText(Paper paper) throws IOException, UnvalidDefinitionException {
		Set<Pair<String, Double>> resourceAbstracts = new HashSet<Pair<String, Double>>();
		for (Pair<KeyPhrase, Double> keyPhrase : keyPhrasesExtractor
				.getKeyPhrases(paper)) {
			resourceAbstracts.add(new Pair<String, Double>(resourceRetriever
					.getDefinitionText(keyPhrase.getFirst()), keyPhrase
					.getSecond()));
		}
		return combine(paper, resourceAbstracts);
	}

	protected abstract String combine(Paper paper,
			Set<Pair<String, Double>> resourceAbstracts);

}
