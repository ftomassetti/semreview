package it.polito.softeng.common.filesystem;

import java.util.LinkedList;
import java.util.List;

public final class UriUtils {
	
	/**
	 * Prevent instantiation.
	 */
	private UriUtils(){		
	}

	
	/**
	 * Splits a URI in blocks. Blocks are separated by slashes. The prefix
	 * 'http://', if present, is considered part of the first block.
	 * 
	 * @param uri
	 * @return
	 */
	public static String[] splitURI(final String uri) {
		List<String> parts = new LinkedList<String>();

		int startIndex = 0;
		int seachStartIndex = 0;
		if (uri.startsWith(HTTP_PREFIX)) {
			seachStartIndex = HTTP_PREFIX.length();
		}

		while (startIndex<uri.length()) {
			int index = uri.indexOf('/', seachStartIndex);
			if (index == -1) {
				parts.add(uri.substring(startIndex));
				seachStartIndex = startIndex = uri.length();
			} else {
				parts.add(uri.substring(startIndex, index));
				seachStartIndex = startIndex = 1+index;				
			}
		}

		return parts.toArray(new String[] {});
	}
	
	/**
	 * Considering the block definitions given at splitURI it takes
	 * the whole URI up to the block indicated.
	 */
	public static String getBlocksUpTo(final String uri, int blockIndex){
		if (blockIndex<0) throw new IllegalArgumentException("Block index should be >=0");
		String[] blocks = splitURI(uri);
		if (blockIndex>=blocks.length) throw new IllegalArgumentException("Block index should be < #blocks of the URI");
		StringBuffer result = new StringBuffer("");
		for (int i=0;i<=blockIndex;i++){
			if (i!=0) {
				result.append("/");
			}
			result.append(blocks[i]);
		}
		return result.toString();
	}

	private static final String HTTP_PREFIX = "http://";
}
