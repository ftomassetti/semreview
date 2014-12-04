package it.polito.semreview.utils;

public final class CsvUtils {
	
	private CsvUtils(){
		
	}
	
	public static String serializeForCsv(String originalText){
		return originalText.replaceAll("\'", "''").replaceAll("\n", "");
	}

}
