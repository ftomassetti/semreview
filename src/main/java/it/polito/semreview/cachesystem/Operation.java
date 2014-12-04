package it.polito.semreview.cachesystem;

/**
 * Generic interface for an operation.
 * @param <R> result of the operation
 */
public interface Operation<R> {
	R execute() throws Exception;
}
