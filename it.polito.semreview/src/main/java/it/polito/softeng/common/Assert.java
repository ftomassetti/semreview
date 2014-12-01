package it.polito.softeng.common;

/**
 * Utility used to declare constraints in the code.
 * It is created to be used to express assertions
 * that are not intended to be disabled at runtime
 * (like the facility inserted in the language).
 * 
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 */
public final class Assert {
	
	private Assert() {
		
	}
	
	/**
	 * If the condition is not verified a {@link AssertionFailureException}
	 * is thrown.
	 * @throws AssertionFailureException
	 */
	public static void asserz(boolean condition){
		if (condition==false){
			throw new AssertionFailureException();
		}
	}
	
	/**
	 * If the condition is not verified an {@link AssertionFailureException}
	 * is thrown.
	 */
	public static void asserz(boolean condition, String conditionDescription){
		if (condition==false){
			throw new AssertionFailureException(conditionDescription);
		}
	}
	
	/**
	 * It indicates that an {@link Exception} was not expected.
	 * @param message reasons for which the Exception was not expected.
	 */
	public static void asserz(Exception unexpectedException, String message){
		throw new AssertionFailureException("Unexpected exception: "+message, unexpectedException);		
	}
	
	/**
	 * It indicates that an {@link Exception} was not expected.
	 */
	public static void asserz(Exception unexpectedException){
		throw new AssertionFailureException("Unexpected exception", unexpectedException);		
	}
	
}
