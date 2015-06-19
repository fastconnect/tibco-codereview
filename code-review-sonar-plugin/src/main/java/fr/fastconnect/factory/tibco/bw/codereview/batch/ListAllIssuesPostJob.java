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

import org.sonar.api.batch.CheckProject;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.resources.Project;

/**
 *
 * @author Mathieu Debove
 *
 */
public class ListAllIssuesPostJob implements PostJob, CheckProject {

	private Settings settings;
	private final ProjectIssues projectIssues;

	public ListAllIssuesPostJob(Settings settings, ProjectIssues projectIssues) {
		this.settings = settings;
		this.settings.toString(); // might be used later
		this.projectIssues = projectIssues;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return Boolean.TRUE;
	}

	@Override
	public void executeOn(Project project, SensorContext context) {
		System.out.println("ListAllIssuesPostJob");

		for (Issue issue : projectIssues.issues()) {
			String ruleKey = issue.ruleKey().toString();
//			String severity = issue.severity();
//			boolean isNew = issue.isNew();
			Integer issueLine = issue.line();

			// just to illustrate, we dump some fields of the 'issue' in sysout
			// (bad, very bad)
			System.out.println(ruleKey + " : " + issue.componentKey() + "("	+ issueLine + ")");
		}
	}
}
