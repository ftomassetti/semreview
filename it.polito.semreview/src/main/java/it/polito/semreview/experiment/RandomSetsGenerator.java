package it.polito.semreview.experiment;

import it.polito.softeng.common.filesystem.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomSetsGenerator {
	
	/**
	 * Build a set of n different elements from 0..max-1 
	 * @param r
	 * @param max
	 * @param nSamples
	 * @return
	 */
	public static Set<Integer> extractSet(Random r, int max, int nSamples){
		Set<Integer> set = new HashSet<Integer>();
		for (int i=0;i<nSamples;i++){
			boolean ok = false;
			while (!ok){
				int extracted = r.nextInt(max);
				ok = !set.contains(extracted);
				if (ok){
					set.add(extracted);
				}
			}			
		}
		return set;
	}
	
	public static Set<Set<Integer>> extractSetOfSets(Random r, int max, int nSamples, int nSets){
		Set<Set<Integer>> set = new HashSet<Set<Integer>>();
		for (int i=0;i<nSets;i++){
			boolean ok = false;
			while (!ok){
				Set<Integer> extracted = extractSet(r, max, nSamples);
				ok = !set.contains(extracted);
				if (ok){
					set.add(extracted);
				}
			}			
		}
		return set;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Random random = new Random(24032011);
		String basePath = args[0];		
		for (int i0Size=1;i0Size<=5;i0Size++){
			int n = 0;
			for (Set<Integer> set : extractSetOfSets(random, 50, i0Size, 30)){
				File file = new File(basePath+File.separator+"size_"+i0Size+File.separator+"n_"+n+".i0");
				if (!file.getParentFile().exists()&&!file.getParentFile().mkdirs()){
					throw new IOException("Can not create dir "+file.getParentFile());
				}
				saveSet(set,file);
				n++;
			}
		}
	}

	private static void saveSet(Set<Integer> set, File file) throws IOException {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (int element : set){
			if (first){
				first = false;
			} else {
				buffer.append(",");
			}
			buffer.append(element);
		}
		FileUtils.saveFile(file, buffer.toString());
	}

}
