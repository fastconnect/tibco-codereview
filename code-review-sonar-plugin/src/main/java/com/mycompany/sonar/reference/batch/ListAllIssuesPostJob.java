package com.mycompany.sonar.reference.batch;

import org.sonar.api.batch.CheckProject;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.resources.Project;

public class ListAllIssuesPostJob implements org.sonar.api.batch.PostJob, CheckProject {

  private Settings settings;
  private final ProjectIssues projectIssues;

  public ListAllIssuesPostJob(Settings settings, ProjectIssues projectIssues) {
    this.settings = settings;
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
      String severity = issue.severity();
      boolean isNew = issue.isNew();
      Integer issueLine = issue.line();

      // just to illustrate, we dump some fields of the 'issue' in sysout (bad, very bad)
      System.out.println(ruleKey + " : " + issue.componentKey() + "(" + issueLine + ")");
    }
  }
}
