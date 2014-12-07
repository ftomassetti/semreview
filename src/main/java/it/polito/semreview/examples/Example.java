package it.polito.semreview.examples;

import it.polito.semreview.dbpedia.DbPediaFacadeImpl;
import it.polito.semreview.dbpedia.NoResourceFoundException;
import it.polito.semreview.dbpedia.InvalidDefinitionException;
import it.polito.semreview.dbpedia.InvalidResponseException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DbPediaFacadeImpl facade = new DbPediaFacadeImpl();

			String abstractText;
			try {
				abstractText = facade.retrieveAbstract("London");
				System.out.println("Abstract: " + abstractText);
			} catch (NoResourceFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDefinitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * DocumentParser documentParser = new DocumentParser();
			 * DbPediaURIRetriever queryExecutor = new
			 * DbPediaURIRetriever(documentParser); Pair<String,Double>[] URIs =
			 * queryExecutor.retrievePossibileURIs("Nobel"); String selectedURI
			 * = null; for (int i=0;i<URIs.length /*&& selectedURI==null
			 *//*
				 * ;i++){ /* if
				 * (DefinitionRetriever.isADbPediaURI(URIs[i].getFirst())){
				 * System
				 * .out.println("Found URI "+URIs[i].getFirst()+", relevance "
				 * +URIs[i].getSecond()); selectedURI = URIs[i].getFirst(); }
				 * else { System.out.println("Found URI "+URIs[i].getFirst()+
				 * " (ignored because not from DbPedia)"); } }/* if
				 * (selectedURI==null){
				 * System.out.println("No suitable URI found"); } else {
				 * System.out.println("Retrieving URI '"+selectedURI+"'");
				 * DefinitionRetriever definitionRetriever = new
				 * DefinitionRetriever(documentParser); Document definition =
				 * definitionRetriever.retrieveDefinition(selectedURI);
				 * 
				 * String abstractText
				 * =DefinitionAnalyzer.getAbstract(selectedURI, definition);
				 * System.out.println(abstractText); }
				 */
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
		} /*
		 * catch (UnvalidDefinitionException e) { e.printStackTrace(); }
		 */
	}

}
