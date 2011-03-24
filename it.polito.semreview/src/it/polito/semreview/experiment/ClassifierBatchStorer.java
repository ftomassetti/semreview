package it.polito.semreview.experiment;

import it.polito.semreview.classifiers.Classifier;
import it.polito.semreview.classifiers.KnowledgeBase;
import it.polito.semreview.classifiers.NaiveBayes;
import it.polito.semreview.dataset.PaperId;
import it.polito.semreview.dataset.PapersDirLoadingStrategy;
import it.polito.softeng.common.FileUtils;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.observerpattern.Observer;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ClassifierBatchStorer {
	
	public static KnowledgeBase getModel(List<String> interestingPapers){
		KnowledgeBase kb = new KnowledgeBase();
		for (String anInterestingPaper : interestingPapers){
			kb.train("interesting", anInterestingPaper);
		}
		return kb;		
	}
	
	public static List<String> getI0() throws IOException{
		List<String> i0 = new LinkedList<String>();
		File i0Dir = new File("\\\\pittore.polito.it\\dataset\\I0\\1");
		for (File paperFile : i0Dir.listFiles()){
			String paperContent = FileUtils.readFile(paperFile);
			i0.add(paperContent);
		}
		return i0;
	}
	
	private static KnowledgeBase knowledgeBase;
	private static Classifier classifier;
	
	public static void main(String[] args) throws IOException {
		knowledgeBase =  getModel(getI0());
		classifier = new NaiveBayes("interesting");
		File enrichedDir = new File(EnricherBatchStorer.ENRICHED_DIR);
		PapersDirLoadingStrategy<String> pdls = new PapersDirLoadingStrategy<String>(enrichedDir, "TSE", "enriched") {

			@Override
			protected String load(PaperId paperId, File file)
					throws LoadingException {
				return SerializationStorage.load(file, String.class);				
			}
		};
		pdls.registerObserver(new Observer<Pair<PaperId,String>>() {
			
			@Override
			public void receiveNotification(Pair<PaperId, String> paper) {
				System.out.println("- "+paper.getFirst()+" : "+classifier.getAffinity(knowledgeBase, paper.getSecond()));
			}
		});
		
		try {
			pdls.getAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
