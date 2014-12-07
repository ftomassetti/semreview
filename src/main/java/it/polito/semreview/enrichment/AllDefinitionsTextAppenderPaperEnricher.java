package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.paper.Paper;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.resourcelookup.ResourceRetriever;
import it.polito.softeng.common.collections.Pair;

import java.util.Set;

/**
 * This PaperEnriched append all the sections of a paper and all the definitions
 * of the KeyPhrases not considering their importance.
 */
public class AllDefinitionsTextAppenderPaperEnricher extends AbstractSemanticPaperEnricher {

	public AllDefinitionsTextAppenderPaperEnricher(
			KeyPhrasesExtractor<Paper> keyPhrasesExtractor,
			ResourceRetriever resourceRetriever) {
		super(keyPhrasesExtractor, resourceRetriever);
	}

	@Override
	protected String combine(Paper paper, Set<Pair<String, Double>> resourceAbstracts) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append(paper.collateText());
		for (Pair<String, Double> resourceDefinition : resourceAbstracts){
			buffer.append(resourceDefinition.getFirst());
		}
		
		return buffer.toString();
	}

}
