package it.polito.softeng.common.exceptions;

public class LoadingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8559923628346250342L;

	public LoadingException(Throwable cause){
		super(cause);
	}
	
	public LoadingException(String explanation){
		super("Loading failed because "+explanation);
	}
	
}
