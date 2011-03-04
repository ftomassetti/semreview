package it.polito.semreview.dataset;

public interface Paper {
	PaperId getId();
	String getTitle();
	String getAbstract();
	String getIntroduction();
	String getConclusions();
	String collateText();
}
