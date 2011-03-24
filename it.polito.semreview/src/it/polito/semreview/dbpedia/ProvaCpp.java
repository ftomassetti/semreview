package it.polito.semreview.dbpedia;

import java.io.IOException;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhraseImpl;

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
