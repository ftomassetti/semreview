package it.polito.softeng.common;

public class StackTraceUtils {
	
	final static String NEW_LINE = System.getProperty("line.separator");

	public static String getCustomStackTrace(Throwable aThrowable) {
		// add the class name and any message passed to constructor
		final StringBuilder result = new StringBuilder();
		result.append(aThrowable.toString());
		
		result.append(NEW_LINE);

		// add each element of the stack trace
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			result.append(element);
			result.append(NEW_LINE);
		}
		return result.toString();
	}

}
