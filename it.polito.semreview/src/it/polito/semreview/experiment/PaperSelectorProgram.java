package it.polito.semreview.experiment;

import it.polito.semreview.utils.CsvUtils;
import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvReader;

public class PaperSelectorProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File f = new File("dataset/KeyPhrasesSelection.csv");
		String data;
		Map<String, Boolean> keyPhrasesLoadedMap = new HashMap<String, Boolean>();
		Map<String, String> keyPhrasesAbstracts = new HashMap<String, String>();
		Map<String, Set<String>> kpsForFile = new HashMap<String, Set<String>>();
		try {
			data = FileUtils.readFile(f);
			CsvReader reader = CsvReader.parse(data);
			reader.setDelimiter(';');
			while (reader.readRecord()) {
				/*
				 * System.out.println("KeyPhrase "+reader.get(0)+"");
				 * System.out.println("\tKeyPhrase "+reader.get(1)+"");
				 * System.out.println("\tKeyPhrase "+reader.get(2)+"");
				 */
				String kp = CsvUtils.unserializeForCsv(reader.get(0));
				// System.out.println("Loaded '"+kp+"'");

				keyPhrasesLoadedMap
						.put(kp, Boolean.parseBoolean(reader.get(1)));
				String abstractText = reader.get(2).substring(1);
				abstractText = abstractText.substring(0,
						abstractText.length() - 1);
				keyPhrasesAbstracts.put(kp, abstractText);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("* Keyphrases loaded");

		int papersOk = 0;
		int papersKo = 0;

		String text = "";

		for (File kpFile : FileUtils.listFile(new File("dataset"),
				new FileNameExtensionFilter(EnricherProgram.KEYPHRASES_EXTENSION),
				true)) {
			BufferedReader reader;
			System.out.println("Working on " + kpFile.getName());
			int good = 0;
			int bad = 0;
			kpsForFile.put(kpFile.getName(), new HashSet<String>());
			try {
				reader = new BufferedReader(new FileReader(kpFile));
				String line = null;
				do {
					line = reader.readLine();
					if (line != null) {

						int index = line.indexOf("###");
						String kp = line.substring(0, index);
						if (!keyPhrasesLoadedMap.containsKey(kp)) {
							throw new RuntimeException("Unexpected '" + kp
									+ "'");
						}
						good += keyPhrasesLoadedMap.get(kp) ? 1 : 0;
						bad += keyPhrasesLoadedMap.get(kp) ? 0 : 1;
						if (keyPhrasesLoadedMap.get(kp)) {
							kpsForFile.get(kpFile.getName()).add(
									keyPhrasesAbstracts.get(kp));
						}
						System.out.println("\t\t" + kp + " good: " + good
								+ ", bad: " + bad);
					}
				} while (line != null);
				text += kpFile.getName().substring(0,
						kpFile.getName().length() - 3)
						+ ";";
				if (good >= 5) {
					System.out.println("\tOK!");
					papersOk++;
					text += "TRUE";
					enrich(kpFile, kpsForFile.get(kpFile.getName()));
				} else {
					System.out.println("\tSCARTATO!");
					papersKo++;
					text += "FALSE";
				}
				text += "\n";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("PapersOk=" + papersOk + ", papersKo="
					+ papersKo);
		}

		try {
			FileUtils.saveFile(new File("PapersSelection.csv"), text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void enrich(File kpFile, Set<String> abstracts)
			throws IOException {
		File aFile = FileUtils.changeExtensionTo(kpFile,
				EnricherProgram.DATAFILE_EXTENSION);
		String text = FileUtils.readFile(aFile);
		for (String abs : abstracts) {
			text += "\n\n" + abs;
		}
		File apFile = FileUtils.changeExtensionTo(kpFile,
				EnricherProgram.ENRICHED_EXTENSION);
		FileUtils.saveFile(apFile, text);
	}

}
