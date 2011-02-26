package it.polito.semreview.dbpedia;

public class NoResourceFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -611391942940142449L;
	private String keyphrase;

	public NoResourceFoundException(String keyphrase) {
		super("No resource found for keyphrase '" + keyphrase + "'");
		this.keyphrase = keyphrase;
	}

	public String getKeyphrase() {
		return keyphrase;
	}
}
