package it.polito.semreview.enrichment.keyphrasesextraction.opencalais;

import it.polito.semreview.dataset.paper.PaperId;
import it.polito.semreview.dataset.paper.PapersDirLoadingStrategy;
import it.polito.semreview.enrichment.keyphrasesextraction.KeyPhrase;
import it.polito.softeng.common.collections.Pair;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.util.HashSet;

public class KeyPhrasesLoadingDirStrategy extends PapersDirLoadingStrategy<HashSet<Pair<KeyPhrase, Double>>>{

	public KeyPhrasesLoadingDirStrategy(File rootDir, String journalName) {
		super(rootDir, journalName,"kp");
	}

	@Override
	protected HashSet<Pair<KeyPhrase, Double>> load(PaperId paperId, File file) throws LoadingException {
		HashSet<Pair<KeyPhrase, Double>> pair = (HashSet<Pair<KeyPhrase, Double>>)SerializationStorage.load(file, HashSet.class);
		return pair;
	}

}
