package it.polito.semreview.enricher;

import it.polito.semreview.dataset.Paper;
import it.polito.semreview.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.resourcelookup.ResourceRetriever;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSemanticPaperEnricher implements PaperEnricher {

	private KeyPhrasesExtractor keyPhrasesExtractor;
	private ResourceRetriever resourceRetriever;
	
	@Override
	public String getEnrichedText(Paper paper) {
		List<String> resourceAbstracts = new LinkedList<String>();
		for (KeyPhrase keyPhrase : keyPhrasesExtractor.getKeyPhrases(paper)){
			resourceAbstracts.add(resourceRetriever.getDefinitionText(keyPhrase));
		}
		return combine(paper, resourceAbstracts);
	}
	
	protected abstract String combine(Paper paper, List<String> resourceAbstracts);

}
