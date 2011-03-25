package it.polito.semreview.experiment;

import it.polito.softeng.common.ExtensionFileNameFilter;
import it.polito.softeng.common.FileUtils;
import it.polito.softeng.common.exceptions.LoadingException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class ClassifierBatchStorerManyThresholdsManyI0s {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, LoadingException {
		if (args.length != 5) {
			System.err
					.println("Args required: <plain papers dir> <enriched papers dir> <I_zero dir> <interesting papers csv> <result file>");
			return;
		}

		File plainPapersDir = new File(args[0]);
		File enrichedDir = new File(args[1]);
		File i0dir = new File(args[2]);
		File csvInterestingPapers = new File(args[3]);
		FilenameFilter filter = new ExtensionFileNameFilter("i0");
		for (File i0File : FileUtils.listFiles(i0dir, filter, true)){
			ClassifierBatchStorerManyThresholds.execute(plainPapersDir, enrichedDir, i0File, csvInterestingPapers, i0File.getAbsolutePath()+"_results_");
		}
	}

}
