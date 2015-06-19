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

import java.util.Arrays;
import java.util.List;

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
			// Initializer to retrieve FC Code Review analysis
			CodeReviewInitialize.class,

			// Batch
			IssueSensor.class,
			ListAllIssuesPostJob.class,

			// UI
			CodeReviewFooter.class, ExampleRubyWidget.class
			);
	}
}
