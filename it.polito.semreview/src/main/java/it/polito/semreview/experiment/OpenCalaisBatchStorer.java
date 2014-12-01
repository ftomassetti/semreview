package it.polito.semreview.experiment;

import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.cachesystem.OperationCaching;
import it.polito.semreview.dataset.DataSetProvider;
import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dataset.PaperId;
import it.polito.semreview.dataset.TextFileDirDataSetProvider;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class OpenCalaisBatchStorer {

	private static final String kpsPath = "D:\\Sandbox\\kps";

	public static File calcKpFile(PaperId paperId) {
		File kpDir = new File(kpsPath + "\\" + paperId.getYear() + "\\issue_"
				+ paperId.getIssue());
		File kpFile = new File(kpDir.getAbsolutePath() + "\\"
				+ paperId.getTitle() + ".kp");
		return kpFile;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String papersPath = "\\\\pittore.polito.it\\dataset\\Paper2";

		DataSetProvider dataSetProvider = new TextFileDirDataSetProvider(
				new File(papersPath), "TSE");

		int papersCount = 0;
		try {
			for (Paper paper : dataSetProvider.getAllPapers()) {
				File kpDir = new File(kpsPath + "\\" + paper.getId().getYear()
						+ "\\issue_" + paper.getId().getIssue());
				if (kpDir.exists() || kpDir.mkdirs()) {
					File kpFile = new File(kpDir.getAbsolutePath() + "\\"
							+ paper.getId().getTitle() + ".kp");
					Operation operation = new OpenCalaisOperation(paper);
					try {
						OperationCaching.get(operation, kpFile, Object.class);
						System.out.println("" + papersCount + ") Ok, "
								+ paper.getId());
						papersCount++;
					} catch (LoadingException e) {
						e.printStackTrace();
					} catch (StoringException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.err.println("No dir " + kpDir);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/*
		 * File dir = new File("D:\\Sandbox\\papers\\2000\\issue_1\\"); File
		 * storeDir = new File("D:\\Sandbox\\kps"); String text = null;
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
