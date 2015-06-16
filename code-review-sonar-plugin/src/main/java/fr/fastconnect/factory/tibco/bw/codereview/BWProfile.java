package fr.fastconnect.factory.tibco.bw.codereview;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.rules.RuleQuery;
import org.sonar.api.utils.ValidationMessages;

public class BWProfile extends ProfileDefinition {

	private static final Logger logger = LoggerFactory.getLogger(BWProfile.class);

	private final RuleFinder ruleFinder;

	public BWProfile(RuleFinder ruleFinder) {
		this.ruleFinder = ruleFinder;
	}

	@Override
	public RulesProfile createProfile(ValidationMessages messages) {
		RulesProfile profile = RulesProfile.create("BW profile", BW.KEY);
		profile.setLanguage(BW.KEY);
		
		RuleQuery query = RuleQuery.create().withRepositoryKey("FCCodeReviewRepository"); 
		Collection<Rule> rulesToActivate = ruleFinder.findAll(query);
		for (Rule rule : rulesToActivate) {
			profile.activateRule(rule, null);
		}

		for (ActiveRule r : profile.getActiveRules()) {
			logger.info("active : " + r.getRuleKey());
		} 
		return profile;
	}
}