package it.polito.semreview.dbpedia;

import org.w3c.dom.Document;

public class UnvalidDefinitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2443243290009090744L;

	public UnvalidDefinitionException(Document definition, String reason) {
		super("Unvalid response obtained: " + reason);
		this.definition = definition;
		this.reason = reason;
	}

	public UnvalidDefinitionException(Document definition, String reason,
			Throwable cause) {
		super("Unvalid response obtained: " + reason, cause);
		this.definition = definition;
		this.reason = reason;
	}

	public Document getDefinition() {
		return definition;
	}

	public String getReason() {
		return reason;
	}

	private final String reason;
	private final Document definition;
}
