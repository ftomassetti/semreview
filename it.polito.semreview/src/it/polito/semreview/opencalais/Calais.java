/**
 * Calais.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.polito.semreview.opencalais;

public interface Calais extends javax.xml.rpc.Service {
	public java.lang.String getcalaisSoapAddress();

	public CalaisSoap getcalaisSoap() throws javax.xml.rpc.ServiceException;

	public CalaisSoap getcalaisSoap(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException;
}
