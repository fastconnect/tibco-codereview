package fr.fastconnect.factory.tibco.bw.codereview.batch;

import fr.fastconnect.factory.tibco.bw.codereview.CodeReviewInitialize;
import fr.fastconnect.factory.tibco.bw.codereview.ExampleMetrics;
import fr.fastconnect.factory.tibco.bw.codereview.BWCodeReviewPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class ExampleSensor implements Sensor {

	private static final Logger logger = LoggerFactory.getLogger(ExampleSensor.class);

	private Settings settings;
	private FileSystem fs;

	/**
	 * Use of IoC to get Settings and FileSystem
	 */
	public ExampleSensor(Settings settings, FileSystem fs) {
		this.settings = settings;
		this.fs = fs;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
//		return fs.hasFiles(fs.predicates().hasLanguage("java"));
		return true;
	}

	public void analyse(Project project, SensorContext sensorContext) {
		logger.info("Analyse sensor");
		if (CodeReviewInitialize.reviewResult != null) {
			logger.info(CodeReviewInitialize.reviewResult.getDuration().toString());
		}
		// This sensor create a measure for metric MESSAGE on each Java file
		String value = settings.getString(BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH);
		logger.info(BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH + "=" + value);
		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			sensorContext.saveMeasure(inputFile, new Measure<String>(ExampleMetrics.MESSAGE, value));
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
