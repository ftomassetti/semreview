package it.polito.softeng.common.exceptions;

public class UnknownElementException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3620322995416346019L;
	private Object element;

	public UnknownElementException(Object element){
		super("Unknown element "+element);
		this.element = element;		
	}
	
	public UnknownElementException(Object element, String additionalExplanation){
		super("Unknown element "+element+": "+additionalExplanation);
		this.element = element;		
	}
	
	public UnknownElementException(Object element, Throwable cause){
		super("Unknown element "+element,cause);
		this.element = element;		
	}

	public Object getElement() {
		return element;
	}

}
