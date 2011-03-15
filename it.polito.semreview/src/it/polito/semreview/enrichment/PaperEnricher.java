package it.polito.semreview.enrichment;

import java.io.IOException;

import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dbpedia.UnvalidDefinitionException;

public interface PaperEnricher {
	String getEnrichedText(Paper paper) throws IOException, UnvalidDefinitionException;
}
