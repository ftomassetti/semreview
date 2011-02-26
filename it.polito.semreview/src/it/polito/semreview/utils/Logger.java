package it.polito.semreview.utils;

/**
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetrò
 * 
 * @deprecated use standard logger instead
 */
@Deprecated
public interface Logger {
	void log(String message);
	void log(String topic, String message);
	void log(String message, Object...value);
}
