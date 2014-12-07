package it.polito.semreview.dataset;

import java.io.IOException;
import java.util.List;

public interface DataSetProvider<D extends Document> {
	List<D> getAllDocuments() throws IOException;
}
