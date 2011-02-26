package it.polito.semreview.experiment;

import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.csvreader.CsvReader;

public class DataSetMap {

	public boolean isInteresting(String paperName) {
		verifyClassification(paperName);

		return interestingMap.get(paperName);
	}

	public void verifyClassification(String paperName) {
		if (!interestingMap.containsKey(paperName))
			throw new RuntimeException("Paper '" + paperName
					+ "' is not classified (I)");
		if (!enrichableMap.containsKey(paperName))
			throw new RuntimeException("Paper '" + paperName
					+ "' is not classified (E)");
	}

	public boolean isEnrichable(String paperName) {
		verifyClassification(paperName);

		return enrichableMap.get(paperName);
	}

	private Map<String, Boolean> enrichableMap = new HashMap<String, Boolean>();
	private Map<String, Boolean> interestingMap = new HashMap<String, Boolean>();

	public DataSetMap() throws IOException {
		loadCsv();
	}

	private void loadCsv() throws IOException {
		File csvFile = new File("dataset/IndexOfEnrichablePapers.csv");
		String csvContent = FileUtils.readFile(csvFile);
		CsvReader reader = CsvReader.parse(csvContent);
		reader.setDelimiter(';');
		int nEnrichable = 0;
		while (reader.readRecord()) {
			String paperName = reader.get(0).substring(1)
					.replaceAll("\'\'", "'");
			paperName = paperName.substring(0, paperName.length() - 1);
			boolean enrichable = Boolean.parseBoolean(reader.get(1));
			boolean interesting = Boolean.parseBoolean(reader.get(2));
			enrichableMap.put(paperName, enrichable);
			interestingMap.put(paperName, interesting);
			if (enrichable)
				nEnrichable++;
			// System.out.println("On csv: "+paperName+", "+enrichable+", "+interesting);

		}
		System.out.println("nEnrichable " + nEnrichable);
	}

}
