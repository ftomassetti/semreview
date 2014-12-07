package it.polito.semreview.enrichment;

import it.polito.semreview.utils.filesystem.FileUtils;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.IOException;

/**
 * This program loads an Enriched file and convert it to text.
 */
public class EnrichedToText {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: EnrichedToText <input dir>");
			System.exit(-2);
		}
		File dir = new File(args[0]);
		if (!dir.exists() || !dir.isDirectory()) {
			System.err.println("Argument given (" + dir.getAbsolutePath()
					+ ") is not a directory or is not existing");
			System.exit(-3);
		}
		convertDir(dir);
	}

	private static void convertDir(File dir) {
		System.out.println("* Converting " + dir.getAbsolutePath());
		for (File inputFile : dir.listFiles()) {
			if (inputFile.isDirectory()) {
				convertDir(inputFile);
			} else {
				if (inputFile.getName().endsWith(".enriched")) {
					File outputFile = FileUtils.changeExtensionTo(inputFile,
							"txt");
					convertFile(inputFile, outputFile);
				}
			}
		}
	}

	private static void convertFile(File inputFile, File outputFile) {
		System.out.println("- " + inputFile.getName() + " -> "
				+ outputFile.getName());
		try {
			String text = SerializationStorage.load(inputFile, String.class);
			FileUtils.saveFile(outputFile, text);
		} catch (LoadingException | IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
    }

}
