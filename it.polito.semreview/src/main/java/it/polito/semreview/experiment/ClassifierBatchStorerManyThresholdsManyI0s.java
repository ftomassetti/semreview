package it.polito.semreview.experiment;

import it.polito.semreview.dataset.PaperId;
import it.polito.softeng.common.filesystem.ExtensionFileNameFilter;
import it.polito.softeng.common.filesystem.FileUtils;
import it.polito.softeng.common.collections.Pair;
import it.polito.softeng.common.exceptions.LoadingException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

public class ClassifierBatchStorerManyThresholdsManyI0s {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, LoadingException {
		if (args.length != 5) {
			System.err
					.println("Args required: <plain papers dir> <enriched papers dir> <I_zero dir> <interesting papers csv> <result file>\n"
							+ "or --notenriched <plain papers dir> <I_zero dir> <interesting papers csv> <result file>");
			return;
		}

		if (args[0].equals("--notenriched")) {
			File plainPapersDir = new File(args[1]);
			File i0dir = new File(args[2]);
			File csvInterestingPapers = new File(args[3]);
			FilenameFilter filter = new ExtensionFileNameFilter("i0");
			Collection<File> i0Files = FileUtils.listFiles(i0dir, filter, true);

			execute(plainPapersDir, i0Files, csvInterestingPapers,
					args[4]);
		} else {

			File plainPapersDir = new File(args[0]);
			File enrichedDir = new File(args[1]);
			File i0dir = new File(args[2]);
			File csvInterestingPapers = new File(args[3]);
			FilenameFilter filter = new ExtensionFileNameFilter("i0");
			Collection<File> i0Files = FileUtils.listFiles(i0dir, filter, true);

			execute(plainPapersDir, enrichedDir, i0Files, csvInterestingPapers,
					args[4]);
		}
	}
	
	public static void execute(File plainPapersDir,
			Collection<File> i0Files, File csvInterestingPapers,
			String resultBasePath) throws IOException, LoadingException {
		ClassifierBatchStorer instance = new ClassifierBatchStorer(
				plainPapersDir, csvInterestingPapers);
		List<Pair<PaperId, String>> plainPapers = instance.loadAllPlain();
		List<Pair<PaperId, String>> papersToExamine = instance.useEnrichedPapers()?instance
				.loadAllEnriched():plainPapers;
		for (File i0File : i0Files) {
			for (float threshold = 0f; threshold <= 1.0f; threshold += 0.01f) {
				String thresholdStr = (new Formatter()).format("%1$.2f",
						threshold).toString();
				System.out.println("THRESHOLD " + thresholdStr);
				File resultFile = new File(i0File.getParent() + File.separator
						+ resultBasePath + "_" + i0File.getName() + "_"
						+ thresholdStr + ".results");
				instance.algorithm(threshold, resultFile, plainPapers,
						papersToExamine, i0File);
			}
		}
	}

	public static void execute(File plainPapersDir, File enrichedDir,
			Collection<File> i0Files, File csvInterestingPapers,
			String resultBasePath) throws IOException, LoadingException {
		ClassifierBatchStorer instance = new ClassifierBatchStorer(
				plainPapersDir, enrichedDir, csvInterestingPapers);
		List<Pair<PaperId, String>> plainPapers = instance.loadAllPlain();
		List<Pair<PaperId, String>> papersToExamine = instance.useEnrichedPapers()?instance
				.loadAllEnriched():plainPapers;
		for (File i0File : i0Files) {
			for (float threshold = 0f; threshold <= 1.0f; threshold += 0.01f) {
				String thresholdStr = (new Formatter()).format("%1$.2f",
						threshold).toString();
				System.out.println("THRESHOLD " + thresholdStr);
				File resultFile = new File(i0File.getParent() + File.separator
						+ resultBasePath + "_" + i0File.getName() + "_"
						+ thresholdStr + ".results");
				instance.algorithm(threshold, resultFile, plainPapers,
						papersToExamine, i0File);
			}
		}
	}
}
