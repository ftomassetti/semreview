package it.polito.semreview.experiment;

import it.polito.semreview.dataset.PaperId;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.LoadingException;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;

public class ClassifierBatchStorerManyThresholds {
	
	public static void execute(File plainPapersDir, File enrichedDir,File i0File,File csvInterestingPapers, String resultBasePath) throws IOException, LoadingException{
		ClassifierBatchStorer instance = new ClassifierBatchStorer(
				plainPapersDir, enrichedDir, csvInterestingPapers);
		List<Pair<PaperId, String>> plainPapers = instance.loadAllPlain();
		List<Pair<PaperId, String>> papersToExamine = instance.loadAllEnriched();
		for (float threshold=0f;threshold<=1.0f;threshold+=0.01f){
			String thresholdStr = (new Formatter()).format("%1$.2f",threshold).toString();
			System.out.println("THRESHOLD "+thresholdStr);
			File resultFile = new File(resultBasePath+"_"+thresholdStr+".results");
			instance.algorithm(threshold, resultFile,plainPapers,papersToExamine, i0File);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, LoadingException {
		if (args.length != 5) {
			System.err
					.println("Args required: <plain papers dir> <enriched papers dir> <I_zero file> <interesting papers csv> <result file>");
			return;
		}

		File plainPapersDir = new File(args[0]);
		File enrichedDir = new File(args[1]);
		File i0File = new File(args[2]);
		File csvInterestingPapers = new File(args[3]);

		execute(plainPapersDir, enrichedDir, i0File, csvInterestingPapers, args[4]);
	}

}
