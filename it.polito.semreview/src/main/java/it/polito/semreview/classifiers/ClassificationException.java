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

/**
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetro'
 */
public class ClassificationException extends Exception {

	private static final long serialVersionUID = 3836657591746504878L;
	
	private String reason;
	
	public String getReason() {
		return reason;
	}

	public ClassificationException(String reason) {
		super("Classification problem due to: " + reason);
		this.reason = reason;
	}

}
