package it.polito.semreview.classifiers;

public interface Classifier {

	/**
	 * Probably "affinity" should be substituted with a better name.
	 * 
	 * @param knowledgeBase
	 * @param document
	 * @return
	 */
	float getAffinity(KnowledgeBase knowledgeBase, String document);
	
}
