package it.polito.semreview.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Luca Ardito
 * @author Giuseppe Rizzo
 * @author Federico Tomassetti
 * @author Antonio Vetrò
 * 
 * @deprecated use standard logger instead
 */
@Deprecated
public class ConsoleLogger implements Logger {


	public void log(String message) {	
		
	}


	public void log(String topic, String message)
	{
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		DateFormat dfm = new SimpleDateFormat("(yy-MM-dd HH:mm:ss)");
		sb.append(dfm.format(now));
		sb.append(" [");
		sb.append(topic);
		sb.append("] - ");
		sb.append(message);
		
		System.out.println(sb.toString());
	}
	
	public void log(String message, Object...value)
	{
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		DateFormat dfm = new SimpleDateFormat("(yy.MM.dd HH:mm:ss)");
		sb.append(dfm.format(now));
		sb.append(" [");
		sb.append(message);
		sb.append("] - ");
		
		for (Object v : value) { 
			sb.append(v.toString());
			sb.append(" ");
		}

		
		System.out.println(sb.toString());
	}	
}
