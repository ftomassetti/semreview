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

import java.util.Hashtable;

/**
 * A Model containing a set of labels and the corresponding instances.
 *
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetro'
 */
public class KnowledgeBase {
	private final Hashtable<String, Label> model;

	public Hashtable<String, Label> getModel() {
		return this.model;
	}

    /**
     * Number of words recorded in the knowledge base. In general each
     * instance recorded through {@link train} can be composed by multiple
     * words
     */
	public int getTotalWords() {
		int totalWords = 0;
        for (Label label : model.values()) {
            totalWords += label.getTotalWords();
        }
        return totalWords;
	}

    /**
     * Associate the given instance to the given label.
     */
	public void train(String labelName, String instance) {
		Label label = model.get(labelName);
		if (label == null) {
			label = new Label(labelName);
			model.put(labelName, label);
		}

		label.addInstance(instance);
	}

	public KnowledgeBase() {
		this.model = new Hashtable<String, Label>();
	}
}
