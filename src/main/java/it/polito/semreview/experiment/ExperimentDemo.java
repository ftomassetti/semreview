package it.polito.semreview.experiment;

import it.polito.semreview.classifiers.Classifier;
import it.polito.semreview.classifiers.KnowledgeBase;
import it.polito.semreview.classifiers.NaiveBayes;
import it.polito.semreview.dataset.DataSetProvider;
import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dataset.XmlDirDataSetProvider;
import it.polito.semreview.dbpedia.DbPediaFacadeImpl;
import it.polito.semreview.dbpedia.UnvalidDefinitionException;
import it.polito.semreview.enrichment.AllDefinitionsTextAppenderPaperEnricher;
import it.polito.semreview.enrichment.PaperEnricher;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.enrichment.keyphrasesextraction.TextToPaperKeyPhrasesExtractorAdapter;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.semreview.resourcelookup.ResourceRetriever;

import java.io.File;
import java.io.IOException;

/**
 * This class shows are different stages of the system can be composed.
 */
public class ExperimentDemo {

	private static File xmlDir;
	
	public static void main(String[] args) throws IOException, UnvalidDefinitionException {
		checkArgs(args);
				
		DataSetProvider<Paper> dataSetProvider = new XmlDirDataSetProvider(xmlDir);
		PaperEnricher paperEnricher = getPaperEnricher();
		KnowledgeBase knowledgeBase = getKnowledgeBase();
		Classifier classifier = getClassifier();
		
		// Stage 1: papers are loaded
		for (Paper paper : dataSetProvider.getAllDocuments()){
			try {
				// Stage 2: papers are enriched 
				String enrichedText = paperEnricher.getEnrichedText(paper);
				
				// Stage 3: papers are classified
				float affinity = classifier.getAffinity(knowledgeBase, enrichedText);
				System.out.println("- Paper "+paper.getId()+" classified with affinity "+affinity);				
			} catch (IOException e) {
				System.err.println("Error: "+e.getMessage());
			}
		}
	}

	private static Classifier getClassifier() {
		return new NaiveBayes("interesting");
	}

	private static KnowledgeBase getKnowledgeBase() {
		throw new UnsupportedOperationException("To be implemented");
	}

	private static PaperEnricher getPaperEnricher(){
		KeyPhrasesExtractor<Paper> keyPhrasesExtractor = new TextToPaperKeyPhrasesExtractorAdapter(new OpenCalaisKeyPhrasesProvider());
		ResourceRetriever resourceRetriever = new DbPediaFacadeImpl();
		PaperEnricher paperEnricher = new AllDefinitionsTextAppenderPaperEnricher(keyPhrasesExtractor,resourceRetriever);
		return paperEnricher;
	}


	private static void checkArgs(String[] args) {
		if (args.length!=2){
			fatalError("ExperimentDemo <XML_DIR>");
		}
		xmlDir = new File(args[1]);
		if (!xmlDir.exists()){
			fatalError("Given path does not exist");
		}
		if (!xmlDir.isDirectory()){
			fatalError("Given path does not correspond to a directory");
		}
	}

	private static void fatalError(String msg){
		System.err.println(msg);
		System.exit(-1);
	}
}
