package it.polito.softeng.common.filesystem;

import java.io.File;
import java.io.FilenameFilter;

public class ExactFileNameFilter implements FilenameFilter {
	
	private final String requestedFilename;

	public ExactFileNameFilter(final String requestedFilename) {
		if (null==requestedFilename){
			throw new NullPointerException();
		}
		this.requestedFilename = requestedFilename;
	}

	@Override
	public boolean accept(File dir, String name) {
		return name.equals(requestedFilename);
	}

}
