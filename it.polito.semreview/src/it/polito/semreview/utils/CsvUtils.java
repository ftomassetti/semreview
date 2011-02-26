package it.polito.semreview.utils;

public final class CsvUtils {
	
	private CsvUtils(){
		
	}
	
	public static String serializeForCsv(String originalText){
		return originalText.replaceAll("\'", "''").replaceAll("\n", "");
	}
	
	public static String unserializeForCsv(String serializedText){
		String text = serializedText.substring(1);
		text = text.substring(0,text.length()-1);
		text = text.replaceAll("\'\'","'");
		return text;
	}

}
