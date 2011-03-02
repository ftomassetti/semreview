package it.polito.semreview.enricher;

import it.polito.semreview.dataset.Paper;

public interface PaperEnricher {
	String getEnrichedText(Paper paper);
}
