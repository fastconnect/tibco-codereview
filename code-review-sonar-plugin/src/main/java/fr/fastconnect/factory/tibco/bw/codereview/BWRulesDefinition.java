package fr.fastconnect.factory.tibco.bw.codereview;

import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

public class BWRulesDefinition implements RulesDefinition {

	public static final String FC_CODEREVIEW_REPOSITORY_KEY = "FCCodeReviewRepository";

	private final RulesDefinitionXmlLoader xmlLoader;

	public BWRulesDefinition(RulesDefinitionXmlLoader xmlLoader) {
		this.xmlLoader = xmlLoader;
	}

	@Override
	public void define(Context context) {

		NewRepository repository = context.createRepository(FC_CODEREVIEW_REPOSITORY_KEY, BW.KEY);
		repository.setName("FCCodeReviewRepository");

		// TODO: add custom XML from runtime
		xmlLoader.load(repository, getClass().getResourceAsStream("/rules/rules.xml"), "UTF-8");

		NewRule rule = repository.createRule("MyRule");
		rule.setName("MyRule");
		rule.setHtmlDescription("<p>This is <em>my rule</em>");
		rule.setStatus(RuleStatus.READY);
		rule.setInternalKey("MyRule");

		NewRule rule2 = repository.createRule("MyRule2");
		rule2.setName("MyRule2");
		rule2.setHtmlDescription("<p>This is <em>my rule 2</em>");
		rule2.setStatus(RuleStatus.READY);
		rule2.setInternalKey("MyRule2");

		repository.done();
	}
}
