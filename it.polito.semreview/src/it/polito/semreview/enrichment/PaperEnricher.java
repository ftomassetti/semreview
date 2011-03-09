package it.polito.semreview.enrichment;

import java.io.IOException;

import it.polito.semreview.dataset.Paper;

public interface PaperEnricher {
	String getEnrichedText(Paper paper) throws IOException;
}
