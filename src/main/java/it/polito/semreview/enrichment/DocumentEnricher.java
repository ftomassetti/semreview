package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.Document;
import it.polito.semreview.dbpedia.InvalidDefinitionException;

import java.io.IOException;

public interface DocumentEnricher<D extends Document> {
	String getEnrichedText(D document) throws IOException, InvalidDefinitionException;
}
