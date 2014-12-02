package it.polito.semreview.enrichment.namedentityextraction.spotlight;

import java.rmi.RemoteException;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.rpc.ServiceException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Spotlight {
	
	private static String URI = "http://spotlight.dbpedia.org/rest/annotate";
	
	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public static String run(String text, double confidence, int support) 
		throws RemoteException, ServiceException 
	{
		return XMLclient(text, confidence, support);
	}
	
	private static String XMLclient(String text, double confidence, int support) 
	{
		Client client = Client.create();
		WebResource webResource = client.resource(URI);

		MultivaluedMap queryParams = new MultivaluedMapImpl();
		queryParams.add("text", text);
		queryParams.add("confidence", "" +confidence);
		queryParams.add("support", "" +support);
		return	webResource.
				queryParams(queryParams).
				accept("text/xml").
				get(String.class);
	}
}
