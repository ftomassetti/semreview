package it.polito.semreview.dbpedia;

public class UnvalidResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2443243290009090744L;

	public UnvalidResponseException(String reason) {
		super("Unvalid response obtained: " + reason);
	}

	public UnvalidResponseException(String reason, Throwable cause) {
		super("Unvalid response obtained: " + reason, cause);
	}

}
