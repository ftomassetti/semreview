package it.polito.semreview.dataset;

import java.io.IOException;
import java.util.List;

public interface DataSetProvider {
	List<Paper> getAllPapers() throws IOException;
}
