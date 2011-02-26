package it.polito.dbpedia;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DocumentParser {

	private DocumentBuilder builder;

	public DocumentParser() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {

			@Override
			public void error(SAXParseException e) throws SAXException {
				throw e;
			}

			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				throw e;
			}

			@Override
			public void warning(SAXParseException e) throws SAXException {
				throw e;
			}
		});
	}
	
	public Document parse(String document) throws SAXException, IOException{
		return builder.parse(new InputSource(new StringReader(document)));
	}
	
	
}
