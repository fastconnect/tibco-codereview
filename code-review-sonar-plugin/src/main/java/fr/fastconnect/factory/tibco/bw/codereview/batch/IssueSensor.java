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
package fr.fastconnect.factory.tibco.bw.codereview.batch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;

import fr.fastconnect.factory.tibco.bw.codereview.BW;
import fr.fastconnect.factory.tibco.bw.codereview.CodeReviewInitialize;
import fr.fastconnect.factory.tibco.bw.codereview.jaxb.Result;
import fr.fastconnect.factory.tibco.bw.codereview.jaxb.RuleType;

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
		this.activeRules.toString(); // could be used
	}

	public boolean shouldExecuteOnProject(Project project) {
		return fs.hasFiles(fs.predicates().hasLanguage(BW.KEY));
	}

	public void analyse(Project project, SensorContext sensorContext) {
		if (CodeReviewInitialize.reviewResult == null || CodeReviewInitialize.reviewResult.getProjectBasedir() == null) return;

		logger.debug("Project basedir : " + CodeReviewInitialize.reviewResult.getProjectBasedir());

		File projectBasedir = new File(CodeReviewInitialize.reviewResult.getProjectBasedir());
		for (RuleType rule : CodeReviewInitialize.reviewResult.getRule()) {
			if (rule.getResult().size() > 0) {
				logger.debug("Found one non conformity : " + rule.getRule().get(0).getKey());
				RuleKey ruleKey = RuleKey.of("FCCodeReviewRepository", rule.getRule().get(0).getKey());
				for (Result result : rule.getResult()) {
					File resourceToFind = new File(projectBasedir.getAbsolutePath() + result.getResource());
					InputFile resource = fs.inputFile(fs.predicates().hasPath(projectBasedir.getAbsolutePath() + result.getResource()));
					if (resource != null) {
						logger.debug("Resource found : " + resourceToFind.getAbsolutePath());
						Issuable issuable = perspectives.as(Issuable.class, resource);
						issuable.addIssue(issuable.newIssueBuilder().ruleKey(ruleKey).line(1).build());
					} else {
						logger.debug("Resource not found : " + resourceToFind.getAbsolutePath());
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
