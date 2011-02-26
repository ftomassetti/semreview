package it.polito.dbpedia;

public class UnvalidDefinitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2443243290009090744L;

	public UnvalidDefinitionException(String reason){
		super("Unvalid response obtained: "+reason);
	}
	
	public UnvalidDefinitionException(String reason, Throwable cause){
		super("Unvalid response obtained: "+reason,cause);
	}
	
}
