package it.polito.softeng.common;

/**
 * It indicates that something unexpected happened.
 * It is intended to be thrown when something that was
 * taken for granted proven to be false and as consequence
 * the task performed should be interrupted.
 * 
 * @author Federico Tomassetti
 */
public class AssertionFailureException extends RuntimeException {
	private static final long serialVersionUID = 8166342112719230911L;

	private static final String MSG = "This assertion proven to be false: ";
	
	private final String assertionDescription;
	
	/**
	 * Assertion failure with no further explanations.
	 */
	public AssertionFailureException(){
		assertionDescription = "<no assertion description provided>";
	}
	
	/**
	 * Assertion failure, description of the assertion
	 * proven to be false provided.
	 * @param assertionDescription 
	 */
	public AssertionFailureException(String assertionDescription){
		super(MSG+assertionDescription);
		this.assertionDescription = assertionDescription;
	}
	
	public final String getAssertionDescription() {
		return assertionDescription;
	}

	public AssertionFailureException(String assertionDescription, Exception unexpectedException){
		super(MSG+assertionDescription,unexpectedException);
		this.assertionDescription = assertionDescription;
	}
	
}
