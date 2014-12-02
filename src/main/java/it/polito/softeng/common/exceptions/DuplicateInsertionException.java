package it.polito.softeng.common.exceptions;

public class DuplicateInsertionException extends RuntimeException {
	
	private static final long serialVersionUID = 303231768027667452L;
	private Object element;

	public DuplicateInsertionException(Object element){
		super("Duplicate insertion of element "+element);
		this.element = element;		
	}

	public Object getElement() {
		return element;
	}
	
}
