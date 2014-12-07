package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.paper.Paper;
import it.polito.semreview.dbpedia.InvalidDefinitionException;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.resourcelookup.ResourceRetriever;
import it.polito.softeng.common.collections.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * To be extended by classes using KeyPhrases to enrich papers.
 */
public abstract class AbstractSemanticPaperEnricher implements PaperEnricher {

	private final KeyPhrasesExtractor<Paper> keyPhrasesExtractor;
	private final ResourceRetriever resourceRetriever;

	public AbstractSemanticPaperEnricher(
			KeyPhrasesExtractor<Paper> keyPhrasesExtractor,
			ResourceRetriever resourceRetriever) {
		this.keyPhrasesExtractor = keyPhrasesExtractor;
		this.resourceRetriever = resourceRetriever;
	}

	@Override
	public String getEnrichedText(Paper paper) throws IOException, InvalidDefinitionException {
		Set<Pair<String, Double>> resourceAbstracts = new HashSet<>();
		for (Pair<KeyPhrase, Double> keyPhrase : keyPhrasesExtractor
				.getKeyPhrases(paper)) {
			resourceAbstracts.add(new Pair<>(resourceRetriever
					.getDefinitionText(keyPhrase.getFirst()), keyPhrase
					.getSecond()));
		}
		return combine(paper, resourceAbstracts);
	}

	protected abstract String combine(Paper paper,
			Set<Pair<String, Double>> resourceAbstracts);

}
