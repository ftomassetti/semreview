package it.polito.softeng.common;

public final class StringUtils {

    /**
     * Prevent instantiation.
     */
	private StringUtils() {

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

}
