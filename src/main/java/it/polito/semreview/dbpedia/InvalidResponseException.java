package it.polito.semreview.dbpedia;

public class InvalidResponseException extends Exception {

	private static final long serialVersionUID = -2443243290009090744L;

	public InvalidResponseException(String reason) {
		super("Unvalid response obtained: " + reason);
	}

	public InvalidResponseException(String reason, Throwable cause) {
		super("Unvalid response obtained: " + reason, cause);
	}

}
