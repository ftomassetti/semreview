package it.polito.semreview.utils;

import java.io.File;
import java.io.FilenameFilter;

public class TXTFileFilter implements FilenameFilter {

	
	public boolean accept(File dir, String name) {
		 
		return name.endsWith(".txt");
	}

}
