package it.polito.semreview.classifiers;

/**
 * Classify a given document either as positive (part of the target set) or negative
 * (not part of the target set).
 */
public interface BinomialClassifier {

    /**
     * Given the current knowledge base, is the document part of the target set?
     */
	boolean accept(KnowledgeBase knowledgeBase, String document);
	
}
