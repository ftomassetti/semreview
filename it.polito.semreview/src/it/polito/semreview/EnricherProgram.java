/*
 * SemReview - A tool to perform semi-automatically systematic reviews using Linked Data. 
 * 
 * Authors: 
 *     Luca Ardito	      <luca.ardito@polito.it>
 *     Giuseppe Rizzo       <giuseppe.rizzo@polito.it>
 *     Federico Tomassetti  <federico.tomassetti@polito.it>
 *     Antonio Vetrò        <antonio.vetro@polito.it>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.polito.semreview;

import it.polito.semreview.dbpedia.DbPediaFacade;
import it.polito.semreview.dbpedia.DbPediaFacadeImpl;
import it.polito.semreview.dbpedia.NoResourceFoundException;
import it.polito.semreview.dbpedia.UnvalidDefinitionException;
import it.polito.semreview.dbpedia.UnvalidResponseException;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.KeyPhrasesProvider;
import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;
import it.polito.softeng.common.Pair;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class EnricherProgram {

	public static final String DATASET_PATH = "dataset";
	public static final String DATAFILE_EXTENSION = "a";
	public static final String KEYPHRASES_EXTENSION = "kp";
	public static final String ENRICHED_EXTENSION = "ap";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EnricherProgram enricher = new EnricherProgram();
		enricher.enrich();
	}

	public KeyPhrasesProvider getKeyPhrasesProvider() {
		return new OpenCalaisKeyPhrasesProvider();
	}

	public double getKeyPhrasesThreshold() {
		return 0.2;
	}

	public DbPediaFacade getDbPediaFacade() {
		return new DbPediaFacadeImpl();
	}

	private boolean quiet = false;

	public void enrich() {
		File dir = new File(DATASET_PATH);
		List<File> files = FileUtils.listFile(dir, new FileNameExtensionFilter(
				DATAFILE_EXTENSION), true);
		for (File f : files) {
			try {
				if (!quiet)
					System.out.println("Enriching " + f.getName());
				String text = FileUtils.readFile(f);
				StringBuffer enrichedText = new StringBuffer();
				enrichedText.append(text);
				enrichedText.append(FileUtils.END_LINE);
				for (Pair<String, Double> keyPhrase : getKeyPhrasesProvider()
						.calculateKeyPhrases(text)) {
					try {
						if (keyPhrase.getSecond() >= getKeyPhrasesThreshold()) {
							if (!quiet)
								System.out.println("\tUsing keyphrase '"
										+ keyPhrase.getFirst() + "'");
							String abstractText = getDbPediaFacade()
									.retrieveAbstract(keyPhrase.getFirst());
							if (!quiet)
								System.out.println("\t\tfound");
							enrichedText.append(abstractText);
							enrichedText.append(FileUtils.END_LINE);
						}
					} catch (UnvalidResponseException e) {
						System.err.println("Unvalid response for '"
								+ keyPhrase.getFirst() + "'");
					} catch (UnvalidDefinitionException e) {
						System.err.println("Unvalid definition for '"
								+ keyPhrase.getFirst() + "', reason: "
								+ e.getReason());
						Document definition = e.getDefinition();
						// System.err.println("Definition obtained: "+definition.);
					}
				}
				File enrichedFile = FileUtils.changeExtensionTo(f,
						ENRICHED_EXTENSION);
				if (!quiet)
					System.out.println("\tsaving to " + enrichedFile.getName());
				// FileUtils.saveFile(enrichedFile, enrichedText.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoResourceFoundException e) {
				System.err.println("Resource not found for keyphrase '"
						+ e.getKeyphrase() + "'");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
