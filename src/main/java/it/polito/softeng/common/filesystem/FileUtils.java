package it.polito.softeng.common.filesystem;

import java.io.*;
import java.util.Collection;
import java.util.Vector;


/**
 * 
 * @author Antonio Vetro'
 * @author Federico Tomassetti
 */
public final class FileUtils {

	public static final String END_LINE = "\n";

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

}
