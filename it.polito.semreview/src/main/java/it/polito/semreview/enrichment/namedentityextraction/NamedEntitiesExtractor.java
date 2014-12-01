package it.polito.semreview.enrichment.namedentityextraction;

import java.util.HashSet;

public interface NamedEntitiesExtractor <E> {
	/**
	 * Explicit type returned just because Set is not Serializable while HashSet is...
	 * @param element
	 * @return
	 */
	
	//TODO index with URI, e.g. Pair<URI,NamedEntity>
	HashSet<NamedEntity> getNamedEntities(E element);

}
