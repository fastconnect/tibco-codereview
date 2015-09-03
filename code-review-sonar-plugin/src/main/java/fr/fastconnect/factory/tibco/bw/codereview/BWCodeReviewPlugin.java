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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.slf4j.Logger;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import fr.fastconnect.factory.tibco.bw.codereview.batch.IssueSensor;
import fr.fastconnect.factory.tibco.bw.codereview.batch.ListAllIssuesPostJob;
import fr.fastconnect.factory.tibco.bw.codereview.ui.CodeReviewFooter;
import fr.fastconnect.factory.tibco.bw.codereview.ui.ExampleRubyWidget;

/**
 * 
 * @author Mathieu Debove
 *
 */
@Properties(
	{
		@Property(key = BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH, name = "Code Review results relative path", description = "Path to the Code Review results relatively to the project path", defaultValue = "target/review")
	}
)
public final class BWCodeReviewPlugin extends SonarPlugin {

	public static final String FC_CODE_REVIEW_RESULTS_RELATIVE_PATH = "fc.codereview.relativePath";

	@SuppressWarnings("rawtypes") // SonarQube API restriction
	public List getExtensions() {
		return Arrays.asList(
			BW.class,
			BWProfile.class,
			BWRulesDefinition.class,

			BWProjectBuilder.class,
			// Initializer to retrieve FC Code Review analysis
			CodeReviewInitialize.class,

			// Batch
			IssueSensor.class,
			ListAllIssuesPostJob.class,

			// UI
			CodeReviewFooter.class, ExampleRubyWidget.class
			);
	}

	private static ClassRealm addConfDirectoryInClasspath(Logger logger) {
		logger.debug("ClassLoader is : " + BWCodeReviewPlugin.class.getClassLoader().getClass().getCanonicalName());
		ClassRealm cr = (ClassRealm) BWCodeReviewPlugin.class.getClassLoader();
		File pluginJar = new File(cr.getURLs()[0].getFile());

		File confDirectory = new File(pluginJar.getParentFile().getAbsolutePath() + "/../../../../conf");
		try {
			cr.addURL(confDirectory.toURI().toURL());
		} catch (java.net.MalformedURLException e) {
			//
		}
		for (URL url : cr.getURLs()) {
			logger.debug("URL : " + url.toString());
		}

		return cr;
	}

	public static InputStream getRulesStream(Logger logger) {
		ClassRealm classRealm = addConfDirectoryInClasspath(logger);
		if (classRealm != null) {

			InputStream customRulesStream = addConfDirectoryInClasspath(logger).getResourceAsStream("rules.xml");
			if (customRulesStream != null) {
				logger.info("Custom rules found");
				return customRulesStream;
			}
		}

		logger.info("Custom rules not found. Using default.");
		return BWCodeReviewPlugin.class.getResourceAsStream("/rules/rules.xml");
	}
}
