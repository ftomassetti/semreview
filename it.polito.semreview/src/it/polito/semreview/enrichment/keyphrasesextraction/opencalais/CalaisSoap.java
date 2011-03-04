/**
 * CalaisSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.polito.semreview.enrichment.keyphrasesextraction.opencalais;

public interface CalaisSoap extends java.rmi.Remote {
	public java.lang.String enlighten(java.lang.String licenseID,
			java.lang.String content, java.lang.String paramsXML)
			throws java.rmi.RemoteException;
}
