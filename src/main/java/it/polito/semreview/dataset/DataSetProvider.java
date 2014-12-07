package it.polito.semreview.dataset;

import java.io.IOException;
import java.util.List;

/**
 * Provide a set of documents.
 */
public interface DataSetProvider<D extends Document> {
	List<D> getAllDocuments() throws IOException;
}
