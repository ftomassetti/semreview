/**
 * CalaisLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.polito.semreview.opencalais;

public class CalaisLocator extends org.apache.axis.client.Service implements
		Calais {

	public CalaisLocator() {
	}

	public CalaisLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public CalaisLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for calaisSoap
	private java.lang.String calaisSoap_address = "http://api.opencalais.com/enlighten/";

	public java.lang.String getcalaisSoapAddress() {
		return calaisSoap_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String calaisSoapWSDDServiceName = "calaisSoap";

	public java.lang.String getcalaisSoapWSDDServiceName() {
		return calaisSoapWSDDServiceName;
	}

	public void setcalaisSoapWSDDServiceName(java.lang.String name) {
		calaisSoapWSDDServiceName = name;
	}

	public CalaisSoap getcalaisSoap() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(calaisSoap_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getcalaisSoap(endpoint);
	}

	public CalaisSoap getcalaisSoap(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			CalaisSoapSoapBindingStub _stub = new CalaisSoapSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getcalaisSoapWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setcalaisSoapEndpointAddress(java.lang.String address) {
		calaisSoap_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (CalaisSoap.class.isAssignableFrom(serviceEndpointInterface)) {
				CalaisSoapSoapBindingStub _stub = new CalaisSoapSoapBindingStub(
						new java.net.URL(calaisSoap_address), this);
				_stub.setPortName(getcalaisSoapWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("calaisSoap".equals(inputPortName)) {
			return getcalaisSoap();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://clearforest.com/",
				"calais");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://clearforest.com/",
					"calaisSoap"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("calaisSoap".equals(portName)) {
			setcalaisSoapEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
