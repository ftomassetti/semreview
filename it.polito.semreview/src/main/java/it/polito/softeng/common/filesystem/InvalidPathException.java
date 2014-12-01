package it.polito.softeng.common.filesystem;

public class InvalidPathException extends Exception {
	
	private static final long serialVersionUID = 3332858290868200075L;
	private final String path;
	private final String reason;

	public final String getPath() {
		return path;
	}

	public final String getReason() {
		return reason;
	}
	
	public InvalidPathException(String path){
		this(path,"<unspecified>");
	}

	public InvalidPathException(String path, String reason){
		super("Path '"+path+"' is invalid because "+reason);
		this.path = path;
		this.reason = reason;
	}
	
}
