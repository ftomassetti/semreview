package it.polito.softeng.common;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public final class StringUtils {

	private StringUtils() {

	}
	
	public static String[] decamelize(String s){
		boolean onlyCapital = true;
		for (int i=0;i<s.length();i++){
			char c = s.charAt(i);
			if (Character.isLowerCase(c)) onlyCapital = false;
		}
		if (onlyCapital){
			return decamelizeCapital(s);
		}
		List<String> strings = new LinkedList<String>();
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length();i++){
			char c = s.charAt(i);
			boolean nextCharUppercase = (s.length()>(i+1))&&Character.isUpperCase(s.charAt(i+1));
			if (Character.isUpperCase(c)&&!nextCharUppercase){
				if (sb.length()>0){
					strings.add(sb.toString().toLowerCase());
					sb = new StringBuffer();
				}				
			} 
			sb.append(c);
		}
		strings.add(sb.toString().toLowerCase());
		return strings.toArray(new String[]{});
	}

	public static String join(String[] elements){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<elements.length;i++){
			sb.append(elements[i]);
		}
		return sb.toString();
	}

	private static String[] decamelizeCapital(String s) {
		List<String> strings = new LinkedList<String>();
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length();i++){
			char c = s.charAt(i);
			if (c=='_'){
				if (sb.length()>0){
					strings.add(sb.toString().toLowerCase());
					sb = new StringBuffer();
				}				
			} else	sb.append(c);
		}
		strings.add(sb.toString().toLowerCase());
		return strings.toArray(new String[]{});
	}

	/**
	 * 
	 * @param args String array of arguments passed to the main
	 * @param message error in case the number of arguments is less then the expected one
	 * @param numberOfExpectedArgs
	 */
	public static void checkNumberOfArguments(String[] args, int numberOfExpectedArgs,String message) {
		if(args.length < numberOfExpectedArgs){
			
			throw new RuntimeException(message);
		}
	}
	

	/**
	 * 
	 * @param c
	 * @return
	 * @since 1.1.1
	 */
	public static boolean isPrintable(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED
				&& block != null && block != Character.UnicodeBlock.SPECIALS;

	}


	public static boolean isFirstUpper(String s) {
		if (s == null)
			throw new IllegalArgumentException("Parameter should not be null");
		if (s.length() < 1)
			throw new IllegalArgumentException(
					"Parameter should be 1 or more characters long");
		return Character.isUpperCase(s.charAt(0));
	}

	public static int occurrences(String s, String piece) {
		if (s == null) {
			throw new NullPointerException(
					"String to examine should be not null");
		}
		if (piece == null) {
			throw new NullPointerException("Piece to find should be not null");
		}
		if (piece == "")
			return 0;
		int index = s.indexOf(piece);
		if (index == -1)
			return 0;
		return 1 + occurrences(restOfString(s, index), piece);
	}

	/**
	 * Stringa a partire dal carattere dopo.
	 */
	public static String restOfString(String s, int index) {
		if (s.length() == index) {
			return "";
		}
		if (s.length() < index) {
			throw new IndexOutOfBoundsException();
		}
		return s.substring(index + 1);
	}

	public static String toFirstLower(String s) {
		if (s.length() < 1)
			return s;
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * Substitute every backslash with a slash.
	 */
	public static String revertBackslashes(String s) {
		return s.replaceAll("\\\\", "/");
	}

	/**
	 * Indice della n-sima occorrenza di un carattere in una stringa. -1 se non
	 * trovato.
	 */
	public static int indexOf(String s, char ch, int occurrence) {
		int i = s.indexOf(ch);
		if (i == -1)
			return -1;
		if (occurrence == 0)
			return i;
		else {
			int subResult = indexOf(s.substring(i + 1), ch, occurrence - 1);
			if (subResult == -1)
				return -1;
			else
				return 1 + i + subResult;
		}
	}

	public static String lastPortion(String s, String delimiter) {
		int index = s.lastIndexOf(delimiter);
		if (index == -1)
			return s;
		if (index == (s.length() - 1)) {
			return "";
		}
		return s.substring(index + 1);
	}

	public static int minLength(List<String> strings) {
		int len = -1;
		for (String s : strings) {
			if (len == -1 || s.length() < len) {
				len = s.length();
			}
		}
		return len;
	}

	public static String commonStart(List<String> strings) {
		if (strings.size() == 0)
			return "";
		if (strings.size() == 1)
			return strings.get(0);
		int len = minLength(strings);
		for (int i = 0; i < len; i++) {
			char ai = strings.get(0).charAt(i);
			for (int j = 1; j < strings.size(); j++) {
				char bi = strings.get(j).charAt(i);
				if (ai != bi) {
					return strings.get(0).substring(0, i);
				}
			}
		}
		return strings.get(0).substring(0, len);
	}

	public static String commonStart(String a, String b) {
		int len = Math.min(a.length(), b.length());
		for (int i = 0; i < len; i++) {
			char ai = a.charAt(i);
			char bi = b.charAt(i);
			if (ai != bi) {
				return a.substring(0, i);
			}
		}
		return a.substring(0, len);
	}

	public static String lastPortion(String s) {
		return lastPortion(s, ".");
	}

	public static String removePostfix(String s, String postfix) {
		if (!s.endsWith(postfix)) {
			throw new IllegalArgumentException("Given string '" + s
					+ "' does not end with given postfix '" + postfix + "'");
		}
		return s.substring(0, s.length() - postfix.length());
	}

	public static String removePrefix(String s, String prefix) {
		if (!s.startsWith(prefix)) {
			throw new IllegalArgumentException("Given string '" + s
					+ "' does not start with given prefix '" + prefix + "'");
		}
		return s.substring(prefix.length());
	}

	public static String toFirstUpper(String s) {
		if (s.length() < 1)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 
	 * @param s
	 *            Original String
	 * @param r
	 *            String that replace characters that are neither letters nor
	 *            numbers
	 * @return a String with no more letters and number
	 */
	public static String keepOnlyLettersAndNumbers(String s, String r) {

		return s.replaceAll("[^a-zA-Z0-9]", r);
	}


	/**
	 * 
	 * @param string
	 * @return the number of white spaces at the beginning of the row
	 */
	public static int computeNumberOfSpacesAtTheBeginOfTheRow(String string) {

		int nSpacesAtTheBeginOfTheRow =0 ;

		for(int j=0; j<string.length();j++)  {

			if(string.charAt(j)==' ')
				nSpacesAtTheBeginOfTheRow++;
			else
				break;

		}
		return nSpacesAtTheBeginOfTheRow;
	}
	
	/**
	 * 
	 * @param string
	 * @return the same string with no white spaces at the beginning of it
	 */
	public static String removeSpacesAtTheBeginning(String string) {
		
		return string.substring(computeNumberOfSpacesAtTheBeginOfTheRow(string), string.length());
	}
	/**
	 * 
	 * @param a string
	 * @return the same string with multiple spaces collapsed in one space
	 * eg input: "I  go  to shop" - output : "I go to shop"
	 */
	 public static String collapseSpaces(String argStr)
	  {
	      char last = argStr.charAt(0);
	      StringBuffer argBuf = new StringBuffer();
	 
	      for (int cIdx = 0 ; cIdx < argStr.length(); cIdx++)
	      {
	          char ch = argStr.charAt(cIdx);
	          if (ch != ' ' || last != ' ')
	          {
	              argBuf.append(ch);
	              last = ch;
	          }
	      }
	 
	      return argBuf.toString();
	  }
	 
	 public static String concat(String[] elements){
		 StringBuffer sb = new StringBuffer();
		 for (int i=0;i<elements.length;i++){
			 if (i!=0){
				 sb.append(", ");				 
			 }
			 sb.append(elements[i]);
		 }
		 return sb.toString();
	 }

	 /** countCharFreq(line) counts freq of an alphabetic char
		 * 
		 * @param line a String
		 * @param c a character
		 * 
		 */

	public static int count(char c, String line) {
		
			int counter = 0;
								
			for (int k = 0; k < line.length(); k++)
			{
				char ch = line.charAt(k);
				
					if( ch == c){
						counter ++;
					}// if()
					
				
			} // for()
	
		return counter;

	}


	public static boolean containsOnlyDigits(String string) {
		
		for(int i=0; i<string.length(); i++){
			
			if (! Character.isDigit(string.charAt(i)) ){
				return false;
			}
				
		}
		
		return true;
	}
}
