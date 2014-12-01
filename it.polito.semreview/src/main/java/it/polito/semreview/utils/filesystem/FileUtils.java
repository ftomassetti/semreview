/*
 * SemReview - A tool to perform semi-automatically systematic reviews using Linked Data. 
 * 
 * Authors: 
 *     Luca Ardito	        <luca.ardito@polito.it>
 *     Giuseppe Rizzo       <giuseppe.rizzo@polito.it>
 *     Federico Tomassetti  <federico.tomassetti@polito.it>
 *     Antonio Vetr�        <antonio.vetro@polito.it>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.polito.semreview.utils.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection of utils methods to manipulate files.
 * 
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetro'
 */
public final class FileUtils {

	public static final String END_LINE = "\n";

	/**
	 * This constructor prevents instantiation.
	 */
	private FileUtils() {

	}

	/**
	 * This method builds a list of file contained in a given directory which
	 * can are accepted by a given <code>FileFilter</code>.
	 * 
	 * @param dir
	 * @param filter
	 * @param recursive
	 *            when this parameter is true also sub-directories are examined.
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 *             if the specified directory does not exist or it is not a
	 *             directory.
	 */
	public static List<File> listFile(File dir, FileFilter filter,
			boolean recursive) {
		if (!dir.exists()) {
			throw new IllegalArgumentException("Illegal path: unexisting");
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Illegal path: not a directory");
		}
		List<File> files = new LinkedList<File>();
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				if (filter.accept(f)) {
					files.add(f);
				}
			} else if (f.isDirectory() && recursive) {
				files.addAll(listFile(f, filter, recursive));
			}
		}
		return files;
	}
	
/*	public static Iterator<File> listFileIteratively(File dir, FileFilter filter,
			boolean recursive) {
		if (!dir.exists()) {
			throw new IllegalArgumentException("Illegal path: unexisting");
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Illegal path: not a directory");
		}
		List<File> files = new LinkedList<File>();
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				if (filter.accept(f)) {
					files.add(f);
				}
			} else if (f.isDirectory() && recursive) {
				files.addAll(listFile(f, filter, recursive));
			}
		}
		return files;
	}

	/*private static class MyDirIterator implements Iterator<File> {
		private File[] files;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public File next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}*/
	
	/**
	 * This method composes a list of name of files. Just the last part of the
	 * name is used, not the path.
	 * 
	 * @param eliminateExtension
	 *            when this parameter is true the extension is removed (also the
	 *            dot point).
	 */
	public static List<String> toFileNames(List<File> files,
			boolean eliminateExtension) {
		List<String> fileNames = new LinkedList<String>();
		for (File f : files) {
			String name = f.getName();
			if (eliminateExtension) {
				int index = name.lastIndexOf('.');
				if (-1 != index) {
					name = name.substring(0, index);
				}
			}
			fileNames.add(name);
		}
		return fileNames;
	}

	/**
	 * This method read a whole file and return the content.
	 * 
	 * @throws IOException
	 *             when an error occurs while reading the file.
	 */
	public static String readFile(File file) throws IOException {
		if (!file.exists()) {
			throw new IllegalArgumentException("Illegal path: unexisting");
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

	public static void saveFile(File file, String text) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(text);
		writer.close();
	}

	public static File changeExtensionTo(File originalFile, String extension) {
		String path = originalFile.getPath();
		int lastDot = path.lastIndexOf('.');
		if (lastDot != -1) {
			path = path.substring(0, lastDot);
		}
		path += "." + extension;
		return new File(path);
	}

}
