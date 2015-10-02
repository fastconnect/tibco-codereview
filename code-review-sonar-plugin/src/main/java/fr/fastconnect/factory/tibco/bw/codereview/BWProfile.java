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

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.rules.RuleQuery;
import org.sonar.api.utils.ValidationMessages;

import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.ObjectFactory;
import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules;

/**
 *
 * @author Mathieu Debove
 *
 */
@SuppressWarnings("deprecation") // as suggested by Sonar team, deprecation of RulesFinder does not apply on server side
public class BWProfile extends ProfileDefinition {

	private static final Logger logger = LoggerFactory.getLogger(BWProfile.class);

	private final RuleFinder ruleFinder;

	public BWProfile(RuleFinder ruleFinder) {
		this.ruleFinder = ruleFinder;
	}

	private Rules loadRules(InputStream is) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Object o =  jaxbUnmarshaller.unmarshal(is);
			return (Rules) o;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RulesProfile createProfile(ValidationMessages messages) {
		RulesProfile profile = RulesProfile.create("BW profile", BW.KEY);
		profile.setLanguage(BW.KEY);

		InputStream rulesStream = BWCodeReviewPlugin.getRulesStream(logger);
		Rules rules = loadRules(rulesStream);

		for (fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules.Rule r : rules.getRule()) {
			org.sonar.api.rules.Rule rule = ruleFinder.find(RuleQuery.create().withRepositoryKey(BWRulesDefinition.FC_CODEREVIEW_REPOSITORY_KEY).withKey(r.getKey()));
			logger.info("activating : " + rule.getKey());
			profile.activateRule(rule, null);
		}

		for (ActiveRule r : profile.getActiveRules()) {
			logger.info("active : " + r.getRuleKey());
		} 
		return profile;
	}

}