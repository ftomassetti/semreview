package it.polito.semreview.dataset;

import it.polito.semreview.utils.filesystem.FileNameExtensionFilter;
import it.polito.semreview.utils.filesystem.FileUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * This class examine a directory and its subdirectories to find xml files
 * which are loaded and Paper are created.
 */
public class XmlDirDataSetProvider implements DataSetProvider {

	private File rootDir;
	
	public XmlDirDataSetProvider(File rootDir){
		if (!rootDir.exists() || !rootDir.isDirectory()){
			throw new IllegalArgumentException("Given path should exist and being a directory");
		}
		this.rootDir = rootDir;
	}
	
	@Override
	public List<Paper> getAllPapers() {
		List<Paper> papers = new LinkedList<Paper>();
		for (File xmlFile : FileUtils.listFile(rootDir,new FileNameExtensionFilter("xml"), true)){
			papers.add(load(xmlFile));
		}
		return papers;
	}
	
	private Paper load(File xmlFile){
		throw new UnsupportedOperationException();
	}

}
