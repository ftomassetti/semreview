package it.polito.semreview.experiment;

import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.cachesystem.OperationCaching;
import it.polito.semreview.dataset.PaperId;
import it.polito.semreview.dataset.TextFileDirDataSetProvider;
import it.polito.semreview.dbpedia.DbPediaBatchStorer;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.softeng.common.FileUtils;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.observerpattern.Observer;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class EnricherBatchStorer {
	
	private static final Logger logger = Logger.getLogger(EnricherBatchStorer.class);
	
	static final String ENRICHED_DIR = "\\\\pittore.polito.it\\dataset\\enriched";
	
	static HashSet<Pair<KeyPhrase,Double>> getKeyPhrases(PaperId paperId) throws LoadingException{
		File kpFile = OpenCalaisBatchStorer.calcKpFile(paperId);
		return SerializationStorage.load(kpFile, HashSet.class);
	}
	
	static String getResourceAbstract(KeyPhrase keyPhrase) throws LoadingException{
		File resFile = DbPediaBatchStorer.calcResFile(keyPhrase);
		if (resFile.exists()){
			return SerializationStorage.load(resFile, String.class);
		} else {
			return null;
		}
	}
	
	static class MyEnrichmentOperation implements Operation<String> {


		private File paperFile;
		private PaperId paperId;
		

		
		public MyEnrichmentOperation(File paperFile,
				PaperId paperId) {
			super();
			this.paperFile = paperFile;
			this.paperId = paperId;
		}



		@Override
		public String execute() throws Exception {
			StringBuffer enrichedText = new StringBuffer();
			logger.info("Going to enrich "+paperId);
			try {
				String paperText = FileUtils.readFile(paperFile);
				enrichedText.append(paperText);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			try {
				HashSet<Pair<KeyPhrase,Double>> keyPhrases = getKeyPhrases(paperId);
				logger.info("KeyPhrases: "+keyPhrases);
				for (Pair<KeyPhrase,Double> kpPair : keyPhrases){
					KeyPhrase kp = kpPair.getFirst();					
					String resourceAbstract = getResourceAbstract(kp);
					logger.info(kp.text()+" = "+resourceAbstract);
					if (resourceAbstract!=null){
						enrichedText.append("\n"+resourceAbstract);
					}
				}
				return enrichedText.toString();
			} catch (LoadingException e1) {
				throw new RuntimeException(e1);
			}

		}
		
	}
	
	static class MyEnricherObserver implements Observer<Pair<PaperId,File>> {

		@Override
		public void receiveNotification(Pair<PaperId, File> paperData) {
			PaperId paperId = paperData.getFirst();
			
			File destFile = new File(ENRICHED_DIR + "\\" + paperId.getYear()
					+ "\\issue_" + paperId.getIssue()+"\\"+paperId.getTitle()+".enriched");
			Operation<String> enrichmentOperation = new MyEnrichmentOperation(paperData.getSecond(),paperId);
			try {
				String enrichedText = OperationCaching.get(enrichmentOperation, destFile, String.class);				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

	public static void main(String[] args){		
		String papersPath = "\\\\pittore.polito.it\\dataset\\Paper2";
				
		// cycle on all text files
		TextFileDirDataSetProvider dataSetProvider = new TextFileDirDataSetProvider(
				new File(papersPath), "TSE");
		// while loading paper we enrich them
		dataSetProvider.registerObserver(new MyEnricherObserver());
		try {
			dataSetProvider.getAllPapers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}