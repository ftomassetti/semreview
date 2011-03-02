package it.polito.semreview.opencalais;

import it.polito.semreview.enricher.EnricherProgram;
import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;
import it.polito.softeng.common.Pair;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpenCalaisRetriever {

	public static void main(String[] args) {
		KeyPhrasesProvider keyPhrasesProvider = new DummyKeyPhrasesProvider();

		File dir = new File(EnricherProgram.DATASET_PATH);
		List<File> files = FileUtils.listFile(dir, new FileNameExtensionFilter(
				EnricherProgram.DATAFILE_EXTENSION), true);
		Set<String> allKps = new HashSet<String>();
		int c = 0;
		int nSkipped = 0;
		for (File f : files) {
			c++;
			try {
				System.out.println("Working on '" + f.getName() + "'");
				String text = FileUtils.readFile(f);
				List<Pair<String, Double>> keyPhrases = keyPhrasesProvider
						.calculateKeyPhrases(text);
				String keyPStringFileContent = "";
				for (Pair<String, Double> kp : keyPhrases) {
					System.out.println("\t* " + kp.getFirst() + " ("
							+ kp.getSecond() + ")");
					keyPStringFileContent = keyPStringFileContent
							+ kp.getFirst() + "###" + kp.getSecond() + "\n";
					allKps.add(kp.getFirst());
				}
				File kpFile = FileUtils.changeExtensionTo(f, "kp");
				FileUtils.saveFile(kpFile, keyPStringFileContent);
				System.out.println("*** processed " + c + " out of "
						+ files.size() + ", kps are " + allKps.size());
			} catch (Exception e) {
				System.err.println("\tError, skip: " + e);
				nSkipped++;
			}
		}
		String t = "";
		for (String kp : allKps) {
			t += kp + "\n";
		}
		try {
			FileUtils.saveFile(new File("LISTA_ALL_KPS.txt"), t);
			System.out.println("Salvata, skippati " + nSkipped);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
