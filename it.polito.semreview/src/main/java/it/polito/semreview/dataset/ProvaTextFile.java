package it.polito.semreview.dataset;

import java.io.File;
import java.io.IOException;

public class ProvaTextFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String pathPittore= "C:\\Users\\FTomassetti\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\SemreviewDataSetSuPittore\\Paper";
		String pathPittore = "\\\\pittore.polito.it\\dataset\\Paper";
		
		DataSetProvider dataSetProvider = new TextFileDirDataSetProvider(new File(pathPittore),"TSE");
		try {
			dataSetProvider.getAllPapers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
