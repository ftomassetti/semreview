package it.polito.semreview.dbpedia;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhraseImpl;

import java.io.IOException;

public class ProvaCpp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbPediaFacadeImpl dbPedia = new DbPediaFacadeImpl();
		try {
			dbPedia.getDefinitionText(new KeyPhraseImpl("C++"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnvalidDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
