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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

/**
 *
 * @author Mathieu Debove
 *
 */
public class BWRulesDefinition implements RulesDefinition {

	private static final Logger logger = LoggerFactory.getLogger(BWRulesDefinition.class);

	public static final String FC_CODEREVIEW_REPOSITORY_KEY = "FCCodeReviewRepository";

	private final RulesDefinitionXmlLoader xmlLoader;

	public BWRulesDefinition(RulesDefinitionXmlLoader xmlLoader) {
		this.xmlLoader = xmlLoader;
	}

	@Override
	public void define(Context context) {
		logger.debug("Creating " + FC_CODEREVIEW_REPOSITORY_KEY + " repository...");
		NewRepository repository = context.createRepository(FC_CODEREVIEW_REPOSITORY_KEY, BW.KEY);
		repository.setName("FCCodeReviewRepository");

		xmlLoader.load(repository, BWCodeReviewPlugin.getRulesStream(logger), "UTF-8");
		repository.done();
	}
}
