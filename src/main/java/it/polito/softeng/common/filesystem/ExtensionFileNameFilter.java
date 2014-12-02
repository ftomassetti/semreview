package it.polito.softeng.common.filesystem;

import java.io.File;
import java.io.FilenameFilter;

public class ExtensionFileNameFilter implements FilenameFilter {

	private String extension;
	
	public ExtensionFileNameFilter(String extension) {
		super();
		this.extension = extension;
	}

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith("."+extension);
	}

}
