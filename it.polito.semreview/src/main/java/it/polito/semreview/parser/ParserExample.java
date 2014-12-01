package it.polito.semreview.parser;

import it.polito.softeng.common.exceptions.LoadingException;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ParserExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Parser.class);
		logger.setLevel(Level.DEBUG);
		ConsoleAppender appender = new ConsoleAppender();
		logger.addAppender(appender);
		
		try {
			Parser.executeToXml("D:\\Sandbox\\in", "D:\\Sandbox\\out");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LoadingException e) {
			e.printStackTrace();
		}
	}

}
