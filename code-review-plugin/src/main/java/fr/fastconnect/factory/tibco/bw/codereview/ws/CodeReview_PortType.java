/**
 * CodeReview_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws;

public interface CodeReview_PortType extends java.rmi.Remote {
    public void review(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.CodeReview codeReview) throws java.rmi.RemoteException;
    public void stopEngine() throws java.rmi.RemoteException;
    public void isStarted() throws java.rmi.RemoteException;
}
