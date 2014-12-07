package it.polito.semreview.dataset;

/**
 * A generic document.
 * @since v0.2
 */
public interface Document<ID> {
    ID getId();
    String[] getSectionNames();
    String getSectionText(String sectioName);
    String collateText();
}
