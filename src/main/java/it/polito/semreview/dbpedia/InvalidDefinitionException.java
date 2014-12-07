package it.polito.semreview.dbpedia;

import org.w3c.dom.Document;

public class InvalidDefinitionException extends Exception {

	private static final long serialVersionUID = -2443243290009090744L;

    private final String reason;
    private final Document definition;

	public InvalidDefinitionException(Document definition, String reason) {
		super("Invalid response obtained: " + reason);
		this.definition = definition;
		this.reason = reason;
	}

	public InvalidDefinitionException(Document definition, String reason,
                                      Throwable cause) {
		super("Invalid response obtained: " + reason, cause);
		this.definition = definition;
		this.reason = reason;
	}

	public Document getDefinition() {
		return definition;
	}

	public String getReason() {
		return reason;
	}
}
