package it.polito.semreview.classifiers;

import java.util.Hashtable;

/**
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetrò
 */
public class NaiveBayes {

	public static Hashtable<String, Double> estimate(String sample,
			KnowledgeBase kb) throws ClassificationException {
		int numberofset = kb.getModel().size();
		// System.out.println(numberofset);
		return (numberofset >= 2) ? binary(sample, kb) : multiclass(sample, kb);
	}

	/******************
	 * NAIVE BAYES FORMULA ************************ * * P(ci) * PROD{ P(wj|ci) }
	 * * P(ci|D) = ---------------------------------- * P(D) * * Likelihood a
	 * prior = Likelihood's class i * production on * each word given class i
	 * (occurrencies'number of word j in * a given class i) divided by
	 * probability of document (we * can interpret this how a mail's likelihood
	 * into dataset) * * *
	 ***************************************************************/

	private static Hashtable<String, Double> multiclass(String sample,
			KnowledgeBase kb) {
		// we build a weighted bagofwords
		String[] tokens = sample.split(" ");
		Hashtable<String, Integer> bagofwords = new Hashtable<String, Integer>();
		for (String token : tokens) {
			if (bagofwords.containsKey(token)) {
				Integer i = bagofwords.get(token);
				i = new Integer(i.intValue() + 1);
			} else
				bagofwords.put(token, new Integer(1));
		}

		Hashtable<String, Label> model = kb.getModel();
		Hashtable<String, Double> score = new Hashtable<String, Double>();

		// model set is a set composed of both Interesting and Excluded papers
		for (Label category : model.values()) {
			int oC = 0; // int pC=0; only if we use not-weighted dictionary in
						// each Label
			for (String word : bagofwords.keySet())
				if (category.getDictionary().containsKey(word))
					oC += category.getDictionary().get(word);

			if (oC != 0) {
				double pwi_ci = Math.log(oC / (1.0 * category.getTotalWords()));
				double pci = Math.log(category.getTotalWords()
						/ (1.0 * kb.getTotalWords()));
				score.put(category.getLabelName(), Math.exp(pwi_ci + pci));
			} else {
				score.put(category.getLabelName(), 0.0);
			}
		}

		return score;
	}

	private static Hashtable<String, Double> binary(String sample,
			KnowledgeBase kb) throws ClassificationException {
		if (kb.getModel().size() < 2)
			throw new ClassificationException(
					"Not enough label to run a binary comparison");

		// we build a weighted bagofwords
		String[] tokens = sample.split(" ");
		Hashtable<String, Integer> bagofwords = new Hashtable<String, Integer>();
		for (String token : tokens) {
			if (bagofwords.containsKey(token)) {
				Integer i = bagofwords.get(token);
				i = new Integer(i.intValue() + 1);
			} else
				bagofwords.put(token, new Integer(1));
		}

		Hashtable<String, Label> model = kb.getModel();
		Hashtable<String, Double> score = new Hashtable<String, Double>();

		// model set is a set composed of both Interesting and Excluded papers
		for (Label i : model.values()) {
			int oI = 0, oE = 0, tI = 0;
			for (Label e : model.values()) {
				if (i.getLabelName() != e.getLabelName()) {
					for (String word : bagofwords.keySet())
						if (e.getDictionary().containsKey(word))
							oE += e.getDictionary().get(word);
				} else {
					for (String word : bagofwords.keySet())
						if (i.getDictionary().containsKey(word))
							oI += i.getDictionary().get(word);

					tI = i.getTotalWords();
				}
			}

			double p_i = 0.0, p_e = 0.0;
			// whether oI is equal to 0
			p_i = (oI != 0) ? oI / (1.0 * tI) : 1 / (1.0 * tI);
			// whether oN is equal to 0
			p_e = (oE != 0) ? oE / (1.0 * (kb.getTotalWords() - tI))
					: 1 / (1.0 * (kb.getTotalWords() - tI));

			score.put(i.getLabelName(), new Double(p_i / 1.0 * (p_i + p_e)));
		}

		return score;
	}
}
