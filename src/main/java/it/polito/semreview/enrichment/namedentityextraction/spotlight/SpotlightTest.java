package it.polito.semreview.enrichment.namedentityextraction.spotlight;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import it.polito.semreview.dbpedia.DbPediaFacadeImpl;
import it.polito.semreview.dbpedia.InvalidDefinitionException;
import it.polito.semreview.dbpedia.InvalidResponseException;
import it.polito.semreview.enrichment.namedentityextraction.NamedEntity;


public class SpotlightTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//			try {
//				Spotlight.run("President Obama called Wednesday on Congress " +
//						"to extend a tax break for students included in last " +
//						"year's economic stimulus package, arguing that the " +
//						"policy provides more generous assistance.",
//						0.5, 
//						20);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		OpenCalaisKeyPhrasesProvider onp = new OpenCalaisKeyPhrasesProvider();
//		onp.getKeyPhrases("President Obama called Wednesday on Congress " +
//				"to extend a tax break for students included in last " +
//				"year's economic stimulus package, arguing that the " +
//				"policy provides more generous assistance.");
		
		
		SpotlightNamedEntityProvider snp = new SpotlightNamedEntityProvider();
		for (NamedEntity e : snp.getNamedEntities("President Obama called Wednesday on Congress " +
						"to extend a tax break for students included in last " +
						"year's economic stimulus package, arguing that the " +
						"policy provides more generous assistance.")){
			System.out.println(e.uri());
			try {
				System.out.println(new DbPediaFacadeImpl().retrieveAbstractFromURI(e.uri()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidResponseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidDefinitionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

}
