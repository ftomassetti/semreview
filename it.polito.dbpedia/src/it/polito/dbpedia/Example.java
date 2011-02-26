package it.polito.dbpedia;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		try {
			DocumentParser documentParser = new DocumentParser();
			DbPediaURIRetriever queryExecutor = new DbPediaURIRetriever(documentParser);
			String[] URIs = queryExecutor.retrivePossibileURIs("Torino");
			String selectedURI = null;
			for (int i=0;i<URIs.length && selectedURI==null;i++){
				if (DefinitionRetriever.isADbPediaURI(URIs[i])){
					selectedURI = URIs[i];
				}
			}
			if (selectedURI==null){
				System.out.println("No suitable URI found");
			} else {
				System.out.println("Retrieving URI '"+selectedURI+"'");
				DefinitionRetriever definitionRetriever = new DefinitionRetriever(documentParser);
				Document definition = definitionRetriever.retrieveDefinition(selectedURI);
				
				String abstractText =DefinitionAnalyzer.getAbstract(selectedURI, definition);
				System.out.println(abstractText);
			}			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnvalidResponseException e) {
			e.printStackTrace();
		} catch (UnvalidDefinitionException e) {
			e.printStackTrace();
		}		
	}

}
