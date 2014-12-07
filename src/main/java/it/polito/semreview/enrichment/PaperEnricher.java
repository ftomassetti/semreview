package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.paper.Paper;
import it.polito.semreview.dbpedia.UnvalidDefinitionException;

import java.io.IOException;

public interface PaperEnricher {
	String getEnrichedText(Paper paper) throws IOException, UnvalidDefinitionException;
}
