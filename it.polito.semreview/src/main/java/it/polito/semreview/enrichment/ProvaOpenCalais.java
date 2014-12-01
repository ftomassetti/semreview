package it.polito.semreview.enrichment;

import it.polito.semreview.dataset.Paper;
import it.polito.semreview.dbpedia.DbPediaFacadeImpl;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrasesExtractor;
import it.polito.semreview.enrichment.keyphrasesextraction.TextToPaperKeyPhrasesExtractorAdapter;
import it.polito.semreview.enrichment.keyphrasesextraction.opencalais.OpenCalaisKeyPhrasesProvider;
import it.polito.semreview.resourcelookup.ResourceRetriever;
import it.polito.semreview.utils.filesystem.FileUtils;
import it.polito.softeng.common.Pair;
import it.polito.softeng.common.exceptions.StoringException;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ProvaOpenCalais {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String pathPittore=
		// "C:\\Users\\FTomassetti\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\SemreviewDataSetSuPittore\\Paper";
		String pathPittore = "D:\\Sandbox\\papers";

		File dir = new File("D:\\Sandbox\\papers\\2000\\issue_1\\");
		File storeDir = new File("D:\\Sandbox\\kps");
		String text = null;
		
		if (!storeDir.exists()){
			storeDir.mkdir();
		}
		
		for (File paperFile : dir.listFiles()) {
			try {
				text = FileUtils
						.readFile(paperFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			OpenCalaisKeyPhrasesProvider openCalais = new OpenCalaisKeyPhrasesProvider();
			Set<Pair<KeyPhrase, Double>> keyPhrases = openCalais
					.getKeyPhrases(text);
			
			
			File storeFile = new File(storeDir.getAbsolutePath()+"\\"+FileUtils.changeExtensionTo(paperFile,"kp").getName());
			try {
				SerializationStorage.store(keyPhrases, storeFile);
			} catch (StoringException e) {				
				e.printStackTrace();
			}
		}

		/*
		 * DataSetProvider dataSetProvider = new TextFileDirDataSetProvider(new
		 * File(pathPittore),"TSE"); PaperEnricher paperEnricher =
		 * getPaperEnricher(); try { int count = 0; for (Paper paper :
		 * dataSetProvider.getAllPapers()){ System.out.println("PAPER "+count);
		 * try { String enriched = paperEnricher.getEnrichedText(paper);
		 * //System.out.println("\n\n*** Enriched ***\n\n"+enriched);
		 * System.out.println(paper.getId().getTitle()+" OK!"); } catch
		 * (IOException e) { System.out.println(paper.getId().getTitle()+" KO");
		 * } catch (UnvalidDefinitionException e) { Document document =
		 * e.getDefinition();
		 * System.out.println(paper.getId().getTitle()+" KO"); } count++; } }
		 * catch (IOException e) { e.printStackTrace(); }
		 * 
		 * PaperImpl fakePaper = new PaperImpl( new PaperId("myJournal", 2050,
		 * 1, "Prof. Antonio Vetro, nobel per l'informatica"));
		 * fakePaper.addSection("fullText",
		 * "Blasts of incoming fire came every few seconds at the edge of this city straddling a strategic highway intersection where rebels have bulldozed berms and filled hundreds of sandbags around two metal green arches marking the western approaches to the city. The barrage offered a loud and ferocious counterpoint to stalled efforts by Western diplomats to agree on help for the retreating rebels, such as a no-flight zone, even as Colonel Qaddafi warned the insurgents on Tuesday that they had only one choice: surrender or flee. Within an hour of the opening salvos, rebels began falling back from the city�s approaches as the shelling came closer to their positions. Some spoke valiantly about drawing a line in the desert sand, but the superior firepower and numbers of the loyalist troops suggested otherwise . The crash of heavy ordnance almost drowned out the cries of a muezzin from the minaret at a frontline mosque"
		 * );
		 */

	}

	private static PaperEnricher getPaperEnricher() {
		KeyPhrasesExtractor<Paper> keyPhrasesExtractor = new TextToPaperKeyPhrasesExtractorAdapter(
				new OpenCalaisKeyPhrasesProvider());
		ResourceRetriever resourceRetriever = new DbPediaFacadeImpl();
		PaperEnricher paperEnricher = new AllDefinitionsTextAppenderPaperEnricher(
				keyPhrasesExtractor, resourceRetriever);
		return paperEnricher;
	}

}
