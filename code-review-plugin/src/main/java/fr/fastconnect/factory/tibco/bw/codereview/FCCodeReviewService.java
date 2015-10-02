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

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.sun.xml.ws.client.ClientTransportException;

import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview_Service;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview_Type;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.BWService;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.ServiceAgentInEngine;

/**
 * <p>
 * CodeReview Service Agent representation.
 * </p>
 *
 * @author Mathieu Debove
 *
 */
class CodeReview_Proxy implements BWService {

	private CodeReview codeReview;

	public CodeReview_Proxy(CodeReview codeReview) {
		this.codeReview = codeReview;
	}

	@Override
	public void isStarted() {
		codeReview.isStarted();
	}

	@Override
	public void stopEngine() {
		codeReview.stopEngine();
	}

	public void runAllTests(CodeReview_Type args) {
		codeReview.review(args);
	}
}

public class FCCodeReviewService extends ServiceAgentInEngine<CodeReview_Proxy> {
	
	private CodeReview codeReview;
	private CodeReview_Proxy codeReviewProxy;

	public FCCodeReviewService(String bwEnginePort) {
		super();

		URL wsdlLocation = FCCodeReviewService.class.getResource("/CodeReview-concrete.wsdl");
		CodeReview_Service codeReviewService = new CodeReview_Service(wsdlLocation, new QName("http://www.fastconnect.fr/FCTibcoFactory/ws/abstract/CodeReview.xsd", "CodeReview"));

		codeReview = codeReviewService.getPort(CodeReview.class);
		codeReviewProxy = new CodeReview_Proxy(codeReview);

		BindingProvider bindingProvider = (BindingProvider) codeReview;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:" + bwEnginePort + "/CodeReview");
	}

	@Override
	public CodeReview_Proxy getService() {
		return codeReviewProxy;
	}

	public boolean review(CodeReview_Type config) {
		if (codeReview == null) {
			return false;
		}

		try {
			codeReview.review(config);
		} catch (ClientTransportException e) {
			return false; // the isStarted method in the WebService is used to ping
		}

		return true;
	}

}
