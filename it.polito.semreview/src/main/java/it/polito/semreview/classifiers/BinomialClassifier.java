package it.polito.semreview.classifiers;

public interface BinomialClassifier {

	boolean accept(KnowledgeBase knowledgeBase, String enrichedText);
	
}
