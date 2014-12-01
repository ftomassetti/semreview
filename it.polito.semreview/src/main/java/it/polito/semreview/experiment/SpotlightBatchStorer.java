package it.polito.semreview.experiment;

import it.polito.semreview.cachesystem.Operation;
import it.polito.semreview.cachesystem.OperationCaching;
import it.polito.semreview.dataset.DataSetProvider;
import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dataset.PaperId;
import it.polito.semreview.dataset.TextFileDirDataSetProvider;
import it.polito.semreview.enrichment.namedentityextraction.NamedEntity;
import it.polito.semreview.enrichment.namedentityextraction.spotlight.SpotlightNamedEntityProvider;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public class SpotlightBatchStorer {

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
					Operation operation = new SpotlightOperation(paper);
					try {
						OperationCaching.get(operation, kpFile, Serializable.class);
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

	}

	private static class SpotlightOperation implements
			Operation<Set<NamedEntity>> {

		private Paper paper;

		public SpotlightOperation(Paper paper) {
			super();
			this.paper = paper;
		}

		public Set<NamedEntity> execute() {
			SpotlightNamedEntityProvider spotlight = new SpotlightNamedEntityProvider();
			Set<NamedEntity> namedEntities = spotlight.getNamedEntities(paper
					.collateText());
			return namedEntities;
		}

	}

}