/**
 * (C) Copyright 2011-2015 FastConnect SAS
 * (http://www.fastconnect.fr/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.fastconnect.factory.tibco.bw.codereview;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.ws.BindingProvider;

import com.sun.xml.ws.client.ClientTransportException;

import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview_Service;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview_Type;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.ServiceAgentInEngine2;

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

class Port implements java.rmi.Remote {

}

public class FCCodeReviewService extends ServiceAgentInEngine2<CodeReview_Service, Port> {
	
	private CodeReview codeReview;

	public FCCodeReviewService(String bwEnginePort) throws ServiceException, MalformedURLException {
		Logger.getLogger("com.sun.xml.internal.ws.wsdl.parser").setLevel(Level.SEVERE);
		Logger.getLogger("com.sun.xml.ws.api.streaming").setLevel(Level.SEVERE);
		Logger.getLogger("com.sun.xml.ws.wsdl").setLevel(Level.SEVERE);
		URL wsdlLocation = FCCodeReviewService.class.getResource("/CodeReview-concrete.wsdl");
		service = new CodeReview_Service(wsdlLocation, new QName("http://www.fastconnect.fr/FCTibcoFactory/ws/abstract/CodeReview.xsd", "CodeReview"));
		codeReview = service.getPort(CodeReview.class);
		BindingProvider bindingProvider = (BindingProvider) codeReview;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:" + bwEnginePort + "/CodeReview");
	}

	@Override
	public boolean isStarted() {
		if (codeReview == null) {
			return false;
		}

		try {
			codeReview.isStarted();
		} catch (ClientTransportException e) {
			return false; // the isStarted method in the WebService is used to ping
		}

		return true;
	}

	public boolean review(CodeReview_Type config) {
		if (codeReview == null) {
			return false;
		}

		try {
			codeReview.review(config);
		} catch (ClientTransportException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	@Override
	public void stopEngine() {
		if (codeReview == null) {
			return;
		}

		try {
			codeReview.stopEngine();
		} catch (Exception e) {
			// nothing
		}
	}

}
