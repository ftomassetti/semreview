package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.Paper;

public interface PaperEnricher {
	String getEnrichedText(Paper paper);
}
