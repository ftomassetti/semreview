package it.polito.softeng.common.exceptions;

public class StoringException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8559923628346250342L;

	public StoringException(Throwable cause){
		super(cause);
	}
	
	public StoringException(String explanation){
		super("Storing failed because "+explanation);
	}
	
}
