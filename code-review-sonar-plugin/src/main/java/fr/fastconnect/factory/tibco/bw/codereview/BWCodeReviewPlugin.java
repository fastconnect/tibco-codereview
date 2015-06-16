package fr.fastconnect.factory.tibco.bw.codereview;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import fr.fastconnect.factory.tibco.bw.codereview.batch.ExampleSensor;
import fr.fastconnect.factory.tibco.bw.codereview.batch.IssueSensor;
import fr.fastconnect.factory.tibco.bw.codereview.batch.ListAllIssuesPostJob;
import fr.fastconnect.factory.tibco.bw.codereview.batch.RandomDecorator;
import fr.fastconnect.factory.tibco.bw.codereview.ui.ExampleFooter;
import fr.fastconnect.factory.tibco.bw.codereview.ui.ExampleRubyWidget;

/**
 * 
 * @author Mathieu Debove
 *
 */
@Properties({ @Property(key = BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH, name = "Code Review results relative path", description = "Path to the Code Review results relatively to the project path", defaultValue = "target/review") })
public final class BWCodeReviewPlugin extends SonarPlugin {

	public static final String FC_CODE_REVIEW_RESULTS_RELATIVE_PATH = "fc.codereview.relativePath";

	@SuppressWarnings("rawtypes") // SonarQube API restriction
	public List getExtensions() {
		return Arrays.asList(
			BW.class,
			BWProfile.class,
			BWRulesDefinition.class,

			BWProjectBuilder.class,
			// Initializer to launch FC Code Review analysis
			CodeReviewInitialize.class,

			// Definitions
			ExampleMetrics.class,

			// Batch
			ExampleSensor.class, RandomDecorator.class, IssueSensor.class,
			ListAllIssuesPostJob.class,

			// UI
			ExampleFooter.class, ExampleRubyWidget.class
			);
	}
}
