package it.polito.fs;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DirList {


	private static final String PATH = "D:\\Repo\\semreview\\MaterialePerEsperimento\\ArticoliFederico";
	
	
	interface FileFilter {
		
		
		boolean accept(File file);
	}
	
	static class FileNameExtensionFilter implements FileFilter {
		
		private String extension;
		
		public FileNameExtensionFilter(String extension){
			this.extension = extension;
		}

		@Override
		public boolean accept(File file) {
			return file.getName().endsWith("."+extension);
		}
		
	}
	
	public List<File> listFile(String dirPath, FileFilter filter, boolean recursive){
		File dir = new File(PATH);
		if (!dir.exists()){
			throw new IllegalArgumentException("Illegal path: unexisting");
		}
		if (!dir.isDirectory()){
			throw new IllegalArgumentException("Illegal path: not a directory");
		}
		List<File> files = new LinkedList<File>();
		for (File f : dir.listFiles()){
			if (f.isFile()){
			if (filter.accept(f)){
				files.add(f);
			}} else if (f.isDirectory() && f.){
				
			}
		}
		return files;
	}
	
	public List<String> toFileNames(List<File> files, boolean eliminateExtension){
		List<String> fileNames = new LinkedList<String>();
		for (File f : files){
			String name = f.getName();
			if (eliminateExtension){
				int index = name.lastIndexOf('.');
				if (-1!=index){
					name = name.substring(0,index);
				}				
			}
			fileNames.add(name);
		}
		return fileNames;
	}
	
	public static void main(String[] args) {
		DirList dl = new DirList();
		for (String name : dl.toFileNames(dl.listFile(PATH, new FileNameExtensionFilter("a"), true),true)){
			System.out.println(name);
		}
	}

}
