/*
 * SemReview - A tool to perform semi-automatically systematic reviews using Linked Data. 
 * 
 * Authors: 
 *     Luca Ardito	        <luca.ardito@polito.it>
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
package it.polito.semreview.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * 
 * TODO verificare inclusione di codice da altre sorgenti in questa classe.
 *
 */
public class Sweeper {

	private HashMap<String, Object> stopwords;

	private String removeMultipleWhitespace(String message) {
		String[] items = message.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String s : items) {
			if (s.trim().length() > 0) {
				sb.append(s).append(" ");
			}
		}
		return sb.toString().trim();
	}

	private String removePunctuaction(String message) {
		return message.replaceAll("\\p{Punct}", " ");
	}

	private String removeStopWords(String message) {
		StringBuilder result = new StringBuilder();
		String[] items = message.split(" ");
		for (int i = 0; i < items.length; i++) {
			if (!stopwords.containsKey(items[i]))
				result.append(items[i] + " ");
		}

		return result.toString().trim();
	}

	/*
	 * Receives in input a message and parse it to remove punctuaction,
	 * multiplewhitespace, stopwords and stem it.
	 */
	public String run(String message) {
		String result = message;
		result = removeStopWords(result);
		result = removePunctuaction(result);
		result = removeMultipleWhitespace(result);		

		result = stemText(result);

		return result;
	}

	private String stemText(String text) {
		SnowballStemmer stemmer = new englishStemmer();

		Reader reader;
		// spazio aggiunto per prendere anche l'ultima parola
		reader = new StringReader(text+" ");
		reader = new BufferedReader(reader);

		StringBuffer input = new StringBuffer();

		StringWriter output = new StringWriter();

		int repeat = 1;

		Object[] emptyArgs = new Object[0];
		int character;
		try {
			while ((character = reader.read()) != -1) {
				char ch = (char) character;
				if (Character.isWhitespace((char) ch)) {
					if (input.length() > 0) {
						String toStem = input.toString();
						stemmer.setCurrent(toStem);
						for (int i = repeat; i != 0; i--) {
							stemmer.stem();
						}
						output.write(stemmer.getCurrent());
						output.write(' ');
						input.delete(0, input.length());
					}
				} else {
					input.append(Character.toLowerCase(ch));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException on String");
		}
		output.flush();
		return output.toString();
	}

	public Sweeper() {
		// we assume only english
		stopwords = new HashMap<String, Object>();

		try {
			FileReader fr = new FileReader(new File("utils/stopwords"));
			BufferedReader br = new BufferedReader(fr);

			String entry;
			while ((entry = br.readLine()) != null)
				stopwords.put(entry, null);

			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
