package it.polito.semreview.dbpedia;

import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.cachesystem.OperationCaching;
import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dataset.PaperId;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.KeyPhrasesLoadingDirStrategy;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.softeng.common.collections.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;
import it.polito.softeng.common.observerpattern.Observer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class DbPediaBatchStorer {

	private static Logger logger = Logger.getLogger(DbPediaBatchStorer.class);
	
	public static File calcResFile(KeyPhrase kp){
		String repulistiKp = kp.text();
		repulistiKp.replaceAll("'+'", "_PLUS_");
		repulistiKp.replaceAll("'\'", "_SLASH_");
		File resFile = new File(resourcesPath + "\\"
				+ repulistiKp + ".res");
		return resFile;
	}
	
	
	private final static String resourcesPath = "D:\\Sandbox\\resources";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String kpsPath = "\\\\pittore.polito.it\\dataset\\kps";
		

		KeyPhrasesLoadingDirStrategy kpls = new KeyPhrasesLoadingDirStrategy(
				new File(kpsPath), "TSE");

		Observer<Pair<PaperId, HashSet<Pair<KeyPhrase, Double>>>> kpsLoadingObserver = new DbPediaResourceRetrieverObserver(resourcesPath);
		kpls.registerObserver(kpsLoadingObserver);

		try {
			kpls.getAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (LoadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		System.out.println("Loaded");

		/*
		 * int papersCount = 0; try { for (Paper paper :
		 * dataSetProvider.getAllPapers()) { File kpDir = new File(kpsPath +
		 * "\\" + paper.getId().getYear() + "\\issue_" +
		 * paper.getId().getIssue()); if (kpDir.exists()||kpDir.mkdirs()) { File
		 * kpFile = new File(kpDir.getAbsolutePath() + "\\" +
		 * paper.getId().getTitle() + ".kp"); Operation operation = new
		 * OpenCalaisOperation(paper); try { OperationCaching.get(operation,
		 * kpFile, Object.class);
		 * System.out.println(""+papersCount+") Ok, "+paper.getId());
		 * papersCount++; } catch (LoadingException e) { e.printStackTrace(); }
		 * catch (StoringException e) { e.printStackTrace(); } } else {
		 * System.err.println("No dir "+kpDir); } } } catch (IOException e1) {
		 * e1.printStackTrace(); } /* File dir = new
		 * File("D:\\Sandbox\\papers\\2000\\issue_1\\"); File storeDir = new
		 * File("D:\\Sandbox\\kps"); String text = null;
		 * 
		 * if (!storeDir.exists()) { storeDir.mkdir(); }
		 * 
		 * for (File paperFile : dir.listFiles()) { try { text =
		 * FileUtils.readFile(paperFile); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * OpenCalaisKeyPhrasesProvider openCalais = new
		 * OpenCalaisKeyPhrasesProvider(); Set<Pair<KeyPhrase, Double>>
		 * keyPhrases = openCalais .getKeyPhrases(text);
		 * 
		 * File storeFile = new File(storeDir.getAbsolutePath() + "\\" +
		 * FileUtils.changeExtensionTo(paperFile, "kp").getName()); try {
		 * SerializationStorage.store(keyPhrases, storeFile); } catch
		 * (StoringException e) { e.printStackTrace(); } }
		 */

	}

	private static final class DbPediaResourceRetrieverObserver implements
			Observer<Pair<PaperId, HashSet<Pair<KeyPhrase, Double>>>> {
		private final String resourcesPath;
		private int good = 0;
		private int total = 0;

		public int getGood() {
			return good;
		}

		public int getTotal() {
			return total;
		}
		
		public int getBad() {
			return total-good;
		}

		private DbPediaResourceRetrieverObserver(String resourcesPath) {
			this.resourcesPath = resourcesPath;
		}

		@Override
		public void receiveNotification(
				Pair<PaperId, HashSet<Pair<KeyPhrase, Double>>> event) {
			PaperId paperId = event.getFirst();
			File resDir = new File(resourcesPath);
			if (resDir.exists() || resDir.mkdirs()) {

				for (Pair<KeyPhrase, Double> kpPair : event.getSecond()) {
					KeyPhrase kp = kpPair.getFirst();
					String repulistiKp = kp.text();
					repulistiKp.replaceAll("'+'", "_PLUS_");
					repulistiKp.replaceAll("'\'", "_SLASH_");
					File resFile = new File(resDir.getAbsolutePath() + "\\"
							+ repulistiKp + ".res");
					Operation operation = new DbPediaOperation(kp);
					try {
						System.out.println("Total: "+total+", Good: "+good);
						total++;
						OperationCaching.get(operation, resFile,
								Serializable.class);
						good++;
					} catch (LoadingException e) {
						e.printStackTrace();
					} catch (StoringException e) {
						e.printStackTrace();
					} catch (UnvalidDefinitionException e) {
						logger.error("Unvalid definition: "+e.getDefinition());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				System.err.println("No dir " + resDir);
			}
		}
	}

	private static class OpenCalaisOperation implements
			Operation<Set<Pair<KeyPhrase, Double>>> {

		private Paper paper;

		public OpenCalaisOperation(Paper paper) {
			super();
			this.paper = paper;
		}

		@Override
		public Set<Pair<KeyPhrase, Double>> execute() {
			OpenCalaisKeyPhrasesProvider openCalais = new OpenCalaisKeyPhrasesProvider();
			Set<Pair<KeyPhrase, Double>> keyPhrases = openCalais
					.getKeyPhrases(paper.collateText());
			return keyPhrases;
		}

	}

}
