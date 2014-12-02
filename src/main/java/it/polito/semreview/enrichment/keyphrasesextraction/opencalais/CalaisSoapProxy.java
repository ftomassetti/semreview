package it.polito.semreview.enrichment.keyphrasesextraction.opencalais;

public class CalaisSoapProxy implements CalaisSoap {
	private String _endpoint = null;
	private CalaisSoap calaisSoap = null;

	public CalaisSoapProxy() {
		_initCalaisSoapProxy();
	}

	public CalaisSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initCalaisSoapProxy();
	}

	private void _initCalaisSoapProxy() {
		try {
			calaisSoap = (new CalaisLocator()).getcalaisSoap();
			if (calaisSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) calaisSoap)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) calaisSoap)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (calaisSoap != null)
			((javax.xml.rpc.Stub) calaisSoap)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public CalaisSoap getCalaisSoap() {
		if (calaisSoap == null)
			_initCalaisSoapProxy();
		return calaisSoap;
	}

	public java.lang.String enlighten(java.lang.String licenseID,
			java.lang.String content, java.lang.String paramsXML)
			throws java.rmi.RemoteException {
		if (calaisSoap == null)
			_initCalaisSoapProxy();
		return calaisSoap.enlighten(licenseID, content, paramsXML);
	}

}