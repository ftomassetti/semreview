package it.polito.semreview.dbpedia;

import it.polito.semreview.utils.CsvUtils;
import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class DbPediaOptimisticResourceFinder {

	public static void main(String[] args) {
		File f = new File("LISTA_ALL_KPS.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			return;
		}
		String line = null;
		int good = 0;
		int bad = 0;
		String text = "";
		try {
			int i = 0;
			do {

				line = reader.readLine();

				if (line != null) {
					String res = tryWith(line);
					text += "'" + CsvUtils.serializeForCsv(line) + "';";
					if (res == null) {
						bad++;
						text += "FALSE;''";
					} else {
						good++;
						text += "TRUE;'" + CsvUtils.serializeForCsv(res) + "'";
					}
					System.out.println(line + " -> " + res);
					text += "\n";
				}
			} while (line != null);
			FileUtils.saveFile(new File("KeyPhrasesSelection.csv"), text);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static String tryWith(String keyphrase)
			throws ParserConfigurationException {
		String expectedURI = DbPediaURIRetriever
				.getCommonlyExpectedURI(keyphrase);

		DocumentParser documentParser = new DocumentParser();
		DefinitionRetriever definitionRetriever = new DefinitionRetriever(
				documentParser);

		try {
			Document definition = definitionRetriever
					.retrieveDefinition(expectedURI);
			System.out.println("YES for " + keyphrase);
			return DefinitionAnalyzer.getAbstract(expectedURI, definition);
		} catch (IOException e) {
			System.out.println("NO for " + keyphrase);
			return null;
		} catch (UnvalidDefinitionException e) {
			System.out.println("NO UNVALID_DEF for " + keyphrase);
			return null;
		} catch (UnvalidResponseException e) {
			System.out.println("NO UNVALID_RES for " + keyphrase);
			return null;
		}

	}

}
