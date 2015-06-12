/**
 * CodeReview_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws;

import javax.xml.namespace.QName;

public class CodeReview_ServiceLocator extends org.apache.axis.client.Service implements fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_Service {
	private static final long serialVersionUID = -7724618803740910813L;

	public CodeReview_ServiceLocator() {
    }


    public CodeReview_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CodeReview_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CodeReview
    private java.lang.String CodeReview_address = "http://localhost:9090/CodeReview";

    public java.lang.String getCodeReviewAddress() {
        return CodeReview_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CodeReviewWSDDServiceName = "CodeReview";

    public java.lang.String getCodeReviewWSDDServiceName() {
        return CodeReviewWSDDServiceName;
    }

    public void setCodeReviewWSDDServiceName(java.lang.String name) {
        CodeReviewWSDDServiceName = name;
    }

    public fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType getCodeReview() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CodeReview_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCodeReview(endpoint);
    }

    public fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType getCodeReview(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReviewBindingStub _stub = new fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReviewBindingStub(portAddress, this);
            _stub.setPortName(getCodeReviewWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCodeReviewEndpointAddress(java.lang.String address) {
        CodeReview_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(@SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReviewBindingStub _stub = new fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReviewBindingStub(new java.net.URL(CodeReview_address), this);
                _stub.setPortName(getCodeReviewWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, @SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CodeReview".equals(inputPortName)) {
            return getCodeReview();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/ws/abstract/CodeReview.xsd", "CodeReview");
    }

    private java.util.HashSet<QName> ports = null;

    public java.util.Iterator<QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<QName>();
            ports.add(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/ws/abstract/CodeReview.xsd", "CodeReview"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("CodeReview".equals(portName)) {
            setCodeReviewEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
