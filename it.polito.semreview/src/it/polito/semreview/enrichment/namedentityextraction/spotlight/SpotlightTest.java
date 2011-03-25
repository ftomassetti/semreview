package it.polito.semreview.enrichment.namedentityextraction.spotlight;


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
		snp.getNamedEntities("President Obama called Wednesday on Congress " +
						"to extend a tax break for students included in last " +
						"year's economic stimulus package, arguing that the " +
						"policy provides more generous assistance.");
		
		
	}

}
