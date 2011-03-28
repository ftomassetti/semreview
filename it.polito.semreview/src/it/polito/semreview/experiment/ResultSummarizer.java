package it.polito.semreview.experiment;



import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class ResultSummarizer {

	private static boolean verbose = true;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length!=3){
			System.err.println("Expecte: <results file dir> <experiment name> <output file>");
		}
		File resultsDir = new File(args[0]);
		String experimentName = args[1];
		File outputFile = new File(args[2]);
		summarize(resultsDir, experimentName, outputFile);
	}
	
	public static void summarize(File resultsDir, String experimentName, File outputFile) throws IOException{
		StringBuffer output = new StringBuffer();
		int fileCount = 0;
		for (File resultFile : FileUtils.listFile(resultsDir, new FileNameExtensionFilter("results"), true)){
			if (verbose){
				System.out.println("File "+(++fileCount));
			}
			if (!resultFile.getName().startsWith(experimentName)){
				break;
			}
			File resultFileDir = resultFile.getParentFile();
			String fileName = resultFile.getName();
			fileName = it.polito.softeng.common.StringUtils.removePrefix(fileName, experimentName+"_");
			int i0extensionIndex = fileName.indexOf(".i0");
			int resultsExtensionIndex = fileName.indexOf(".results");
			if (i0extensionIndex==-1||resultsExtensionIndex==-1){
				System.err.println("Irregular results file name: "+resultFile);
				System.exit(1);
			}
			String ioSetName = fileName.substring(0,i0extensionIndex);
			File ioSet = new File(resultFileDir.getPath()+File.separator+ioSetName+".i0");
			if (!ioSet.exists()){
				System.err.println("I0 set file does not exist: "+ioSet.getAbsolutePath());
				System.exit(1);
			}
			int i0Size = FileUtils.readFile(ioSet).split(",").length;
			String i0SetId = ""+i0Size+"_"+ioSetName;
			float threshold = Float.parseFloat(fileName.substring(i0extensionIndex+4,resultsExtensionIndex));
			Result result = Result.load(resultFile);
			output.append(""+i0Size+",\""+i0SetId+"\","+threshold+","+result.getTotalPositives()+","+result.getTruePositives().size()+"\n");
		}
		FileUtils.saveFile(outputFile, output.toString());
	}
	
	private static class Result {
		public static Result load(File resultsFile) throws IOException{
			Set<Integer> truePositives = new HashSet<Integer>();
			int totalPositives = 0;
			String content = FileUtils.readFile(resultsFile);
			StringReader r = new StringReader(content);
			BufferedReader br = new BufferedReader(r);
			String line = null;
			int state = ITERATION_STATE;
			while (null!=(line=br.readLine())){
				if (line.trim().length()==0) break;
				switch (state){
				case ITERATION_STATE:
					// do nothing
					break;
				case FILTERED_STATE:
					if (!line.startsWith("filtered:")){
						System.err.println("Error in file "+resultsFile);
						System.exit(2);
					}
					int filtered = Integer.parseInt(line.substring("filtered:".length()));
					totalPositives+=filtered;
					break;
				case TP_STATE:
					String[] parts = line.split(",");
					for (String part : parts){
						try {
						int tp = Integer.parseInt(part);
						truePositives.add(tp);
						} catch (NumberFormatException e){
							System.err.println("Error parsing line '"+line+"'");
							System.exit(2);
						}
					}
					break;
				}
				state = (state+1)%N_STATES;
			}
			return new Result(truePositives,totalPositives);
		}
		
		private static final int ITERATION_STATE = 0;
		private static final int FILTERED_STATE = 1;
		private static final int TP_STATE = 2;
		private static final int N_STATES = 3;
		
		private Result(Set<Integer> truePositives,int totalPositives){
			this.truePositives = truePositives;
			this.totalPositives = totalPositives;
		}
		
		public Set<Integer> getTruePositives() {
			return truePositives;
		}

		public int getTotalPositives() {
			return totalPositives;
		}

		@Override
		public String toString() {
			return "Result [truePositives=" + truePositives
					+ ", totalPositives=" + totalPositives + "]";
		}

		private Set<Integer> truePositives;
		private int totalPositives;
	}

}
