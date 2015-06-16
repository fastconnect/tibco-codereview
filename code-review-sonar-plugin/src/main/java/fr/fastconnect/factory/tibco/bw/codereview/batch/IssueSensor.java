package fr.fastconnect.factory.tibco.bw.codereview.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;

//@DependedUpon(DecoratorBarriers.ISSUES_ADDED)
public class IssueSensor implements Sensor {

	private static final Logger logger = LoggerFactory.getLogger(IssueSensor.class);

	private final FileSystem fs;
	private final ResourcePerspectives perspectives;

	private ActiveRules activeRules;

	/**
	 * Use of IoC to get FileSystem
	 */
	public IssueSensor(FileSystem fs, ResourcePerspectives perspectives, ActiveRules activeRules) {
		this.fs = fs;
		this.perspectives = perspectives;
		this.activeRules = activeRules;
	}

	public boolean shouldExecuteOnProject(Project project) {
		// return fs.hasFiles(fs.predicates().hasLanguage("java"));
		return true;
	}

	public void analyse(Project project, SensorContext sensorContext) {
		// This sensor create an issue on each java file
		logger.info(fs.baseDir().toString());

		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			logger.info("1"+inputFile.absolutePath());
			if (fs.hasFiles(fs.predicates().hasLanguage("bw"))) {
				logger.info("BW!!!");
			}
			Issuable issuable = perspectives.as(Issuable.class, inputFile);
			RuleKey ruleKey = RuleKey.of("FCCodeReviewRepository", "MyRule");
			RuleKey ruleKey2 = RuleKey.of("FCCodeReviewRepository", "MyRule2");
			ActiveRule r = activeRules.find(ruleKey);
			ActiveRule r2 = activeRules.find(ruleKey2);

			issuable.addIssue(issuable.newIssueBuilder().ruleKey(ruleKey).message("Bla bla").line(1).build());
			issuable.addIssue(issuable.newIssueBuilder().ruleKey(ruleKey2).message("Bla bla2").line(1).build());
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
