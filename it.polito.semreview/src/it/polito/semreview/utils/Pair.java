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

public class Pair<T,U> {
	
	private T first;
	private U second;

	public Pair(T first, U second){
		this.first = first;
		this.second = second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	public T getFirst() {
		return first;
	}

	public U getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
	
}
