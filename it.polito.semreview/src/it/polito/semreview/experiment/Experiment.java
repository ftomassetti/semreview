package it.polito.semreview.experiment;

import it.polito.semreview.classifiers.ClassificationException;
import it.polito.semreview.classifiers.KnowledgeBase;
import it.polito.semreview.classifiers.NaiveBayes;
import it.polito.semreview.enricher.Enricher;
import it.polito.semreview.utils.Sweeper;
import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Experiment {

	public static void main(String[] args) {
		try {
			new Experiment().start();
		} catch (ClassificationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The manual iteration classifies correctly the given papers.
	 */
	public List<Paper> manualIteration(List<Paper> candidatesSet) {
		List<Paper> selected = new LinkedList<Paper>();
		for (Paper paper : candidatesSet) {
			String paperName = paper.getName();
			if (dataSetMap.isInteresting(paperName)) {
				selected.add(paper);
			}
		}
		return selected;
	}

	public List<Paper> classifierIteration(double threshold, List<Paper> wSet,
			KnowledgeBase kb) throws IOException, ClassificationException {

		int truePositives = 0;
		int falsePositives = 0;
		int trueNegatives = 0;
		int falseNegatives = 0;

		List<Paper> listOfPositives = new LinkedList<Paper>();
		for (Paper paper : wSet) {
			String text = paper.getText();
			text = (new Sweeper()).run(text);
			double classificationScore = NaiveBayes.estimate(text, kb).get(
					INTERESTING_CLASS);

			String paperName = paper.getName();

			boolean classifiedAsPositive = classificationScore >= threshold;
			boolean interesting = dataSetMap.isInteresting(paperName);

			if (classifiedAsPositive) {
				listOfPositives.add(paper);
				if (interesting)
					truePositives++;
				else
					falsePositives++;
			} else {
				if (interesting)
					falseNegatives++;
				else
					trueNegatives++;
			}

			// System.out.println("'"+paperName.replaceAll("\'",
			// "''")+"';"+classificationScore+";"+interestingMap.get(paperName));
		}
		/*
		 * System.out.println("Threshold " + threshold);
		 * System.out.println("\tTP " + truePositives + ", FP " + falsePositives
		 * + ", TN " + trueNegatives + ", FN " + falseNegatives);
		 */
		int all = trueNegatives + truePositives + falseNegatives
				+ falsePositives;
		double precision = (double) truePositives
				/ (double) (truePositives + falsePositives);
		double recall = (double) truePositives
				/ (double) (truePositives + falseNegatives);
		double fMeasure = 2 * (precision * recall) / (precision + recall);
		double accuracy = (double) (truePositives + trueNegatives)
				/ ((double) all);
		/*
		 * System.out.println("\tPrecision " + precision);
		 * System.out.println("\tRecall " + recall);
		 * System.out.println("\tF-Measure " + fMeasure);
		 * System.out.println("\tAccuracy " + accuracy);
		 */
		return listOfPositives;
	}

	public Paper getNotEnrichedPaper(String paperTitle) throws IOException {
		String fileName = "dataset/" + paperTitle + "."
				+ Enricher.DATAFILE_EXTENSION;
		String text = FileUtils.readFile(new File(fileName));
		return new Paper(paperTitle, text);
	}

	public AlgorithmResult executeAlgorithmIterations(double threshold,
			List<Paper> wSet, List<Paper> I0) throws IOException,
			ClassificationException {
		KnowledgeBase kb = train(I0);

		int nIterations = 0;
		int nPapersExamined = 0;
		List<Paper> iSet = new LinkedList<Paper>();

		boolean newPositive = true;

		while (newPositive) {

			// Classifico
			List<Paper> positivelyClassified = classifierIteration(threshold,
					wSet, kb);
			wSet.removeAll(positivelyClassified);

			// simulazione della supervisione umana, scegliendo i paper
			// realmente interessanti
			List<Paper> truePositives = manualIteration(positivelyClassified);
			newPositive = truePositives.size() > 0;

			List<Paper> falsePositives = new LinkedList<Paper>();
			falsePositives.addAll(positivelyClassified);
			falsePositives.removeAll(truePositives);

			// Riaddestro: I true positives addestrano modello
			for (Paper aTruePositive : truePositives) {
				Paper notEnrichedPaper = getNotEnrichedPaper(aTruePositive
						.getName());
				kb = train(aTruePositive, kb);
				// kb = train(notEnrichedPaper, kb);

			}
			iSet.addAll(truePositives);

			nPapersExamined += positivelyClassified.size();
			nIterations++;

		}
		return new AlgorithmResult(nIterations, nPapersExamined, iSet.size());
	}

	private DataSetMap dataSetMap;

	private List<Paper> buildIZero() throws IOException {
		List<Paper> enrichableInteresting = getPapers(false, true, true);

		List<Paper> I0 = new LinkedList<Paper>();
		I0.addAll(notEnrichablePositiveFiles());
		I0.add(enrichableInteresting.get(7));
		I0.add(enrichableInteresting.get(11));
		I0.add(enrichableInteresting.get(16));
		System.out.println("I0 " + enrichableInteresting.get(7).getName());
		System.out.println("I0 " + enrichableInteresting.get(11).getName());
		System.out.println("I0 " + enrichableInteresting.get(16).getName());
		return I0;
	}

	private void filterI0(List<Paper> l, List<Paper> I0) {
		for (int i = 0; i < l.size(); i++) {
			Paper p = l.get(i);
			boolean found = false;
			for (Paper i0file : I0) {
				if (i0file.getName().equals(p.getName())) {
					found = true;
				}
			}
			if (found) {
				l.remove(i);
				i--;
			}
		}
	}

	public void start() throws ClassificationException {
		try {
			// Loading classification of papers as enrichable or not enrichable
			// and as interesting and not interesting
			// results are stored in
			dataSetMap = new DataSetMap();

			// Builing I_0 with 5 papers out or not enrichable positive files
			List<Paper> I0 = buildIZero();

			// prendo tutti gli arricchibili (prima interessanti e poi gli
			// aggiungo i non interessanti)
			List<Paper> enrichablePapers_enriched = getPapers(true, true, true);
			enrichablePapers_enriched.addAll(getPapers(true, true, false));
			System.out.println("Enrichable enriched not filtered: "
					+ enrichablePapers_enriched.size());
			filterI0(enrichablePapers_enriched, I0);
			System.out.println("Enrichable enriched filtered: "
					+ enrichablePapers_enriched.size());

			List<Paper> enrichablePapers_notEnriched = getPapers(false, true,
					true);
			enrichablePapers_notEnriched.addAll(getPapers(false, true, false));
			System.out.println("Enrichable not enriched not filtered: "
					+ enrichablePapers_notEnriched.size());
			filterI0(enrichablePapers_notEnriched, I0);
			System.out.println("Enrichable not enriched filtered: "
					+ enrichablePapers_notEnriched.size());

			String csvResultsText = "";

			for (double threshold = 0.0; threshold <= 1.0; threshold += 0.01) {

				List<Paper> wSet = new LinkedList<Paper>();
				wSet.addAll(enrichablePapers_notEnriched);

				AlgorithmResult resNotEnriched = executeAlgorithmIterations(
						threshold, wSet, I0);

				wSet = new LinkedList<Paper>();
				wSet.addAll(enrichablePapers_enriched);

				AlgorithmResult resEnriched = executeAlgorithmIterations(
						threshold, wSet, I0);

				System.out.println("Threshold " + threshold);
				System.out.println("\tResult not enriched " + resNotEnriched);
				System.out.println("\tResult enriched " + resEnriched);

				csvResultsText += "" + threshold;
				csvResultsText += ";" + resNotEnriched.getnIterations() + ";"
						+ resNotEnriched.getiSize() + ";"
						+ resNotEnriched.getnPapersExamined();
				csvResultsText += ";" + resEnriched.getnIterations() + ";"
						+ resEnriched.getiSize() + ";"
						+ resEnriched.getnPapersExamined();
				csvResultsText += "\n";
			}

			FileUtils.saveFile(new File("results.csv"), csvResultsText);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String INTERESTING_CLASS = "interesting";

	KnowledgeBase train(List<Paper> iZero) throws IOException {
		KnowledgeBase kb = new KnowledgeBase();
		return train(iZero, kb);
	}

	KnowledgeBase train(List<Paper> iZero, KnowledgeBase kb) throws IOException {
		for (Paper iZeroFile : iZero) {
			train(iZeroFile, kb);
		}
		return kb;
	}

	KnowledgeBase train(Paper paper, KnowledgeBase kb) throws IOException {
		String text = paper.getText();
		text = (new Sweeper()).run(text);
		kb.train(INTERESTING_CLASS, text);
		return kb;
	}

	List<Paper> notEnrichablePositiveFiles() throws IOException {
		return getPapers(false, false, true);
	}

	String toPaperName(File f) {
		String paperName = f.getName();
		paperName = paperName.substring(0, paperName.length() - 2);
		return paperName;
	}

	List<Paper> getPapers(boolean enriched, boolean desiredEnrichable,
			boolean desiredInteresting) throws IOException {

		if (enriched && !desiredEnrichable) {
			throw new RuntimeException("Illegal request");
		}

		if (getPapersLoggy)
			System.out.println("[Looking for version of papers "
					+ (enriched ? "ENR" : "!ENR") + ", from paper "
					+ (desiredEnrichable ? "" : "NOT ") + "enrichable and "
					+ (desiredInteresting ? "" : "NOT ") + "interesting]");

		String extension = enriched ? Enricher.ENRICHED_EXTENSION
				: Enricher.DATAFILE_EXTENSION;

		List<Paper> selectedPapers = new LinkedList<Paper>();

		int enrichableSum = 0;
		int interestingSum = 0;

		if (getPapersLoggy)
			System.out.println("[\tPossible files are "
					+ FileUtils.listFile(new File("dataset"),
							new FileNameExtensionFilter(extension), true)
							.size() + "]");
		for (File aFile : FileUtils.listFile(new File("dataset"),
				new FileNameExtensionFilter(extension), true)) {

			String paperName = aFile.getName();
			paperName = paperName.substring(0, paperName.length() - 2
					+ (enriched ? -1 : 0));

			dataSetMap.verifyClassification(paperName);

			if (dataSetMap.isEnrichable(paperName))
				enrichableSum++;

			if (!dataSetMap.isEnrichable(paperName) && enriched)
				throw new RuntimeException(
						"Non deve succedere, e' arrichito ma non arrichibile "
								+ aFile.getName());

			if (dataSetMap.isInteresting(paperName))
				interestingSum++;

			if (desiredEnrichable == dataSetMap.isEnrichable(paperName)
					&& desiredInteresting == dataSetMap
							.isInteresting(paperName)) {
				selectedPapers.add(new Paper(paperName, FileUtils
						.readFile(aFile)));
			}
		}

		if (getPapersLoggy)
			System.out.println("[\tinteresting " + interestingSum + "]");
		if (getPapersLoggy)
			System.out.println("[\tenrichable " + enrichableSum + "]");
		if (getPapersLoggy)
			System.out.println("[\tfound " + selectedPapers.size() + "]");

		return selectedPapers;
	}

	private boolean getPapersLoggy = true;

}
