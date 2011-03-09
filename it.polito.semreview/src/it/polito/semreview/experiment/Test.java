package it.polito.semreview.experiment;

import it.polito.semreview.dataset.DataSetProvider;
import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dataset.XmlDirDataSetProvider;
import it.polito.semreview.enrichment.PaperEnricher;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.enrichment.keyphrasesextraction.TextToPaperKeyPhrasesExtractorAdapter;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.semreview.resourcelookup.ResourceRetriever;

import java.io.File;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Carico i paper
		DataSetProvider dataSetProvider = new XmlDirDataSetProvider(new File("mia_dir_dei_file_xml"));

		KeyPhrasesExtractor<Paper> keyPhrasesExtractor = new TextToPaperKeyPhrasesExtractorAdapter(new OpenCalaisKeyPhrasesProvider());
//		ResourceRetriever resourceRetriever
//		PaperEnricher paperEnricher = new AllDefinitionsTextAppenderPaperEnricher();
	}

}
