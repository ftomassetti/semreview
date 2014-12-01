package it.polito.semreview.enrichment;

import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ProvaCarimentoKps {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File storeDir = new File("D:\\Sandbox\\kps\\2000\\issue_1");
		for (File kpFile : storeDir.listFiles()){
			try {
				Set<Pair<KeyPhrase, Double>> kps = SerializationStorage.load(kpFile, HashSet.class);
				System.out.println("KPS of size "+kps.size());
				for (Pair<KeyPhrase, Double> p : kps){
					System.out.println("\t"+p.getFirst()+" "+p.getSecond());
				}
			} catch (LoadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
