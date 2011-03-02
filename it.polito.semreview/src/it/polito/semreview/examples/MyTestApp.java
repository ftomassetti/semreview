package it.polito.semreview.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import org.tartarus.snowball.SnowballStemmer;

public class MyTestApp {
	private static void usage() {
		System.err
				.println("Usage: TestApp <algorithm> <input file> [-o <output file>]");
	}

	public static void main(String[] args) throws Throwable {
		/*
		 * if (args.length < 2) { usage(); return; }
		 */

		Class stemClass = Class.forName("org.tartarus.snowball.ext."
				+ "englishStemmer");
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

		Reader reader;
		reader = new StringReader("my studying names is giuseppe!");
		reader = new BufferedReader(reader);

		StringBuffer input = new StringBuffer();

		OutputStream outstream;

		if (args.length > 2) {
			if (args.length >= 4 && args[2].equals("-o")) {
				outstream = new FileOutputStream(args[3]);
			} else {
				usage();
				return;
			}
		} else {
			outstream = System.out;
		}
		Writer output = new OutputStreamWriter(outstream);
		output = new BufferedWriter(output);

		int repeat = 1;
		if (args.length > 4) {
			repeat = Integer.parseInt(args[4]);
		}

		Object[] emptyArgs = new Object[0];
		int character;
		while ((character = reader.read()) != -1) {
			char ch = (char) character;
			if (Character.isWhitespace((char) ch)) {
				if (input.length() > 0) {
					stemmer.setCurrent(input.toString());
					for (int i = repeat; i != 0; i--) {
						stemmer.stem();
					}
					output.write(stemmer.getCurrent());
					output.write('\n');
					input.delete(0, input.length());
				}
			} else {
				input.append(Character.toLowerCase(ch));
			}
		}
		output.flush();
	}
}
