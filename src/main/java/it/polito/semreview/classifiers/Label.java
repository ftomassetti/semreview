/*
 * SemReview - A tool to perform semi-automatically systematic reviews using Linked Data. 
 * 
 * Authors:
 *     Luca Ardito
 *     Giuseppe Rizzo
 *     Federico Tomassetti
 *     Antonio Vetro'
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
package it.polito.semreview.classifiers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * A group of elements with an associated name.
 *
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetro'
 */
public class Label {
	private final String name;
	private int totalWords;
	private final List<String> instances;
	private final Map<String, Integer> dictionary;

	public String getLabelName() {
		return this.name;
	}

	public int getTotalWords() {
		return this.totalWords;
	}

	public Map<String, Integer> getDictionary() {
		return this.dictionary;
	}

	public void addInstance(String instance) {
		// insert into instances array (the string is the atomic value)
		this.instances.add(instance);

		// insert into dictionary (the word is the atomic value)
		String[] words = instance.split(" ");
		for (String word : words) {
			if (!dictionary.containsKey(word)) {
                dictionary.put(word, 1);
            } else {
				Integer previous = dictionary.get(word);
				dictionary.put(word, new Integer(previous + 1));
			}
		}

		// count the number of words in this label
		this.totalWords += instance.split(" ").length;
	}

	public Label(String name) {
		this.name = name;
		this.totalWords = 0;
		this.instances = new ArrayList<String>();
		this.dictionary = new Hashtable<String, Integer>();
	}
}
