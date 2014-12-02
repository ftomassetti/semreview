package it.polito.softeng.common.filesystem;

import java.io.File;
import java.util.List;

public final class PathUtils {
	
	private PathUtils() {
	}

	public static String absoluteFilePath(String base, String relative)
			throws InvalidPathException {
		if (relative.startsWith("../") || relative.startsWith("..\\")) {
			if (relative.length() <= 3) {
				throw new InvalidPathException(relative);
			}
			if (!hasParent(base)) {
				throw new InvalidPathException(relative,
						"refers to the parent directory but used with root as base");
			}
			return absoluteFilePath(parent(base), relative.substring(3));
		}
		if (relative.startsWith("./") || relative.startsWith(".\\")) {
			if (relative.length() <= 2) {
				throw new InvalidPathException(relative);
			}
			return absoluteFilePath(base, relative.substring(2));
		}
		return base + File.separator + relative;
	}

	public static String parent(String path) {		
		File file = new File(path);
		String parent = file.getParent();
		if (null==parent){
			throw new IllegalArgumentException("Given path '"+path+"' has not a parent");
		}
		return parent;
	}

	public static boolean hasParent(String path) {
		File file = new File(path);
		return null != file.getParent();
	}
	
	public static boolean hasExtension(String filename, List<String> extensions){
		for (String e : extensions){
			if (hasExtension(filename, e)) return true;
		}
		return false;
	}
	
	public static boolean hasExtension(String filename, String extension){
		return filename.endsWith("."+extension);
	}

}
