package it.polito.semreview.dataset;

public interface Paper {
	PaperId getId();
	String[] getSectionNames();
	String getSectionText(String sectioName);
	String collateText();
}
