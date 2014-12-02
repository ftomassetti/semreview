package it.polito.softeng.common.filesystem;

import it.polito.softeng.common.Assert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Vector;


/**
 * 
 * @author Antonio Vetro'
 * @author Federico Tomassetti
 */
public final class FileUtils {

	public static final String END_LINE = "\n";
	private static final String CSV_DEFAULT_SEPARATOR = ",";

	private FileUtils() {

	}

	/**
	 * This method read a whole file and return the content.
	 * 
	 * @throws java.io.IOException when an error occurs while reading the file.
	 */
	public static String readFile(File file) throws IOException {
		if (!file.exists()) {
			throw new IllegalArgumentException("Illegal path: unexisting '"+file.getPath()+"'");
		}
		if (!file.isFile()) {
			throw new IllegalArgumentException("Illegal path: not a dfile");
		}
		FileReader fr = null;
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);

			String line = null;
			do {
				line = reader.readLine();
				if (line != null) {
					buffer.append(line);
					buffer.append(END_LINE);
				}
			} while (line != null);

		} finally {
			reader.close();
			fr.close();
		}
		return buffer.toString();
	}

	public static void saveFile(File file, String text) throws IOException{
		FileWriter writer = new FileWriter(file);
		writer.write(text);
		writer.close();
	}
	
	public static String getExtension(String path){
		int lastSlash = path.lastIndexOf('/');
		int lastBackSlash = path.lastIndexOf('\\');
		int lastPortion = -1;
		if (lastSlash!=-1 && lastBackSlash!=-1){
			lastPortion = Math.max(lastSlash, lastBackSlash);
		} else if (lastSlash==-1 && lastBackSlash==-1){
			lastPortion = -1;
		} else if (lastSlash==-1 && lastBackSlash!=-1){ 
			lastPortion=lastBackSlash;
		} else {
			lastPortion=lastSlash;
		}
		if (lastPortion==path.length()-1){
			return null;
		}
		path = path.substring(lastPortion+1);
		int lastDot = path.lastIndexOf('.');
		if (lastDot!=-1){
			if (lastDot==path.length()-1){
				return "";
			} else {
				return path.substring(lastDot+1);
			}
		} else {
			return null;
		}
	}
	
	public static String getExtension(File file){
		return getExtension(file.getPath());
	}

	public static File changeExtensionTo(File originalFile, String extension){
		String path = originalFile.getPath();
		int lastDot = path.lastIndexOf('.');
		if (lastDot!=-1){
			path = path.substring(0,lastDot);
		}
		path += "."+extension;
		return new File(path);
	}

	/**
	 * @param f File to deleted  
	 * It works on both directories and normal files.
	 */
	public static void delete(File f) {
		if (!f.exists())
			return;
		if (f.isDirectory()) {
			deleteDir(f);
		} else {
			if (!f.delete()) {
				throw new RuntimeException("Not deleted: " + f.getPath());
			}
		}
		Assert.asserz(!f.exists());
	}

	/**
	 * 
	 * @param name Path to temporary file 
	 * @return object File corresponding to the temp file created 
	 */
	public static File createTempDir(String name) {
		String tmpDirPath = System.getProperty("java.io.tmpdir");
		if (null == tmpDirPath) {
			throw new RuntimeException(
			"No temp dir specified (property java.io.tmpdir)");
		}
		File tmpDir = new File(tmpDirPath + File.separator + name);
		if (tmpDir.exists()) {
			deleteDir(tmpDir);
		}
		if (!tmpDir.mkdir()) {
			throw new RuntimeException("No temp dir created (at "
					+ tmpDir.getAbsolutePath() + ")");
		}
		Assert.asserz(tmpDir.exists());
		Assert.asserz(tmpDir.isDirectory());
		return tmpDir;
	}

	/**
	 * @param f File to be created
	 * It creates also the directory tree 
	 * @throws java.io.IOException
	 */
	public static void create(File f) throws IOException {
		if (!f.getParentFile().exists()) {
			if (!f.getParentFile().mkdirs()) {
				throw new RuntimeException("Parent dir not created for "
						+ f.getAbsolutePath());
			}
		}
		try {
			if (!f.createNewFile()) {
				throw new RuntimeException("File not created: "
						+ f.getAbsolutePath());
			}
		} catch (IOException e) {
			throw new IOException("File '" + f.getAbsolutePath()
					+ "' not created", e);
		}
	}

	/**
	 * @param dir directory to be deleted
	 * Deletes a directory even if not empty.
	 */
	public static void deleteDir(File dir) {
		if (!dir.exists())
			throw new IllegalArgumentException("Given dir does not exist: "
					+ dir.getPath());
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Given dir is not a directory: "
					+ dir.getPath());
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				deleteDir(f);
			} else {
				if (!f.delete()) {
					throw new RuntimeException("Not deleted: " + f.getPath());
				}
			}
		}
		if (!dir.delete())
			throw new RuntimeException("Not deleted " + dir.getPath());
		Assert.asserz(!dir.exists());
	}

	/**
	 * 
	 * @param directory : parent directory
	 * @param filter : a FileName filter, null if you are interested in retrieving all files 
	 * @param recurse : if true collects also files from subdirs  
	 * @return a collection containing all files in the directory
	 */
	public static Collection<File> listFiles(File directory,
			FilenameFilter filter, boolean recurse) {
		Vector<File> files = new Vector<File>();

		File[] entries = directory.listFiles();

		for (File entry : entries) {
			// If there is no filter or the filter accepts the
			// file / directory, add it to the list
			if (filter == null || filter.accept(directory, entry.getName())) {
				files.add(entry);
			}

			// If the file is a directory and the recurse flag
			// is set, recurse into the directory
			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}

		return files;
	}

	/**
	 * 
	 * @param path  of the file to open
	 * @param lines to append
	 * @throws java.io.IOException
	 */
	public static void appendLineInFile(String path, String lines)
	throws IOException {
		FileOutputStream fouts = null;
		DataOutputStream douts = null;
		try {
			fouts = new FileOutputStream(path, true);
			douts = new DataOutputStream(fouts);
			douts.writeBytes(lines.toString());
			douts.flush();
		} finally {
			if (douts != null)
				douts.close();
			if (fouts != null)
				fouts.close();
		}
	}

	/**
	 * 
	 * @param outFile csv file to initialize   
	 * @param firstRow to put at the begin of the file
	 * @throws java.io.IOException
	 */
	public static void initializeCSV(File outFile, String firstRow)
	throws IOException {
		FileWriter fw = new FileWriter(outFile);
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw.write(firstRow);
			bw.flush();
		} finally {
			fw.close();
			bw.close();
		}

	}

	/**
	 * 
	 * @param sourceDir
	 * @param destDir
	 * @throws java.io.IOException
	 */
	public static void copyDirectory(File sourceDir, File destDir)
	throws IOException {
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		File[] children = sourceDir.listFiles();
		for (File sourceChild : children) {
			String name = sourceChild.getName();
			File destChild = new File(destDir, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, destChild);
			} else {
				copyFile(sourceChild, destChild);
			}
		}
	}

	/**
	 * 
	 * @param source File to be copied
	 * @param dest File where to put the copy of source
	 * @throws java.io.IOException
	 */
	public static void copyFile(File source, File dest) throws IOException {
		if (!dest.exists()) {
			dest.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

		} finally {
			in.close();
			out.close();
		}
	}

	/**
	 * 
	 * @param name of the file
	 * @param filesCollection collection of Files
	 * @return
	 */
	public static File getFileFromCollection(String name,
			Collection<File> filesCollection) {
		for (File file : filesCollection) {
			if (file.getName().equalsIgnoreCase(name)){
				return file;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param path of the file to create, it exits if file already exists
	 * @throws java.io.IOException
	 * 
	 */
	public static File createFile(String path) throws IOException {
		File f = new File(path);
		if (f.exists()) {
			throw new IllegalArgumentException("Cannot create " + path + " - file already existing");
		} else {
			boolean created = f.createNewFile();
			if (!created) {
				throw new IOException("Cannot create " + path);
			}
		}
		return f;

	}

	/**
	 * 
	 * @param outputPath path of File to be created, null if operation fails
	 * @param firstRow of the file, adding \n if necessary
	 */
	public static  File createAndInitializeCSV(String outputPath, String firstRow) {
		File output = null;
		try {

			output = FileUtils.createFile(outputPath);

			if(firstRow.endsWith("\n")){
				FileUtils.initializeCSV(output, firstRow);
			}else
			{
				FileUtils.initializeCSV(output, firstRow+"\n");
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * 
	 * @param srFile source file path to be copied
	 * @param dtFile destination file path 
	 */
	public static void copyfile(String srFile, String dtFile){
		  try{
		  File f1 = new File(srFile);
		  File f2 = new File(dtFile);
		  InputStream in = new FileInputStream(f1);
		  
		  //For Append the file.
		//  OutputStream out = new FileOutputStream(f2,true);

		  //For Overwrite the file.
		  OutputStream out = new FileOutputStream(f2);

		  byte[] buf = new byte[1024];
		  int len;
		  while ((len = in.read(buf)) > 0){
		  out.write(buf, 0, len);
		  }
		  in.close();
		  out.close();
		  System.out.println("File copied.");
		  }
		  catch(FileNotFoundException ex){
		  System.out.println(ex.getMessage() + " in the specified directory.");
		  System.exit(0);
		  }
		  catch(IOException e){
		  System.out.println(e.getMessage());  
		  }
		  }
	
	/**
	 * 
	 * @param originalFile original file
	 * @param newFileContent  the new contetn of the file
	 * @param newExtension the new extension of the back up copy
	 * @throws java.io.IOException
	 * 
	 * Copy the original file f into a f.newextension, and replace the content of f with new content
	 */
	public static void replaceFileContent(File originalFile, StringBuffer newFileContent, String newExtension)
			throws IOException {
		
		
		FileUtils.copyFile(originalFile, changeExtensionTo(originalFile, newExtension));
		originalFile.delete();			
		originalFile.createNewFile();
		FileUtils.appendLineInFile(originalFile.getAbsolutePath(), newFileContent.toString() );
	}

	public static void checkFileExistsAndNotAdirectory(File csvFile) {
		if(!csvFile.exists()){
			throw new RuntimeException("File " + csvFile.getAbsolutePath() + " does not exist");
		}else if(csvFile.isDirectory()){
			
			throw new RuntimeException("File " + csvFile.getAbsolutePath() + " is a directory");
		}
	}
	
	
}
