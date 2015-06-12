package fr.fastconnect.factory.tibco.bw.codereview;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_PortType;
import fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_Service;
import fr.fastconnect.factory.tibco.bw.codereview.ws.CodeReview_ServiceLocator;
import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.CodeReview;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.ServiceAgentInEngine;

/**
 * 
 * FCUnit possède un Service Agent qui permet d'invoquer les processes
 * d'exécution des tests comme des méthodes d'un Web Service.<br/>
 * Cette classe définit les méthodes pour les différents appels possibles qui sont
 * ensuite délégués aux éléments du framework Axis (consommation de Web Services),
 * contenus dans le package "fr.fastconnect.tibco.businessworks.fcunit.plugin.ws"<br/>
 * Cette classe concrète est destiné à être passé en paramètre du constructeur
 * de {@link BWEngine}.
 * 
 * @author Mathieu Debove
 *
 */
public class FCCodeReviewService extends ServiceAgentInEngine<CodeReview_Service, CodeReview_PortType> {
	private CodeReview_Service service;
	private CodeReview_PortType port;
	
	public FCCodeReviewService(String bwEnginePort) throws ServiceException {
		service = new CodeReview_ServiceLocator();
		((CodeReview_ServiceLocator) service).setEndpointAddress("CodeReview", "http://localhost:"+bwEnginePort+"/CodeReview");
		port = service.getCodeReview();
	}

	@Override
	public boolean isStarted() {
		if (port == null) {
			return false;
		}

		try {
			port.isStarted();
		} catch (RemoteException e) {
			return false; // the isStarted method in the WebService is used to ping
		}
		return true;
	}

	public boolean review(CodeReview config) {
		if (port == null) {
			return false;
		}
		
		try {
			port.review(config);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void stopEngine() {
		if (port == null) {
			return;
		}
		try {
			port.stopEngine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
