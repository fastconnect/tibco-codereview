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
import java.io.IOException;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.joox.JOOX;
import org.joox.Match;
import org.xml.sax.SAXException;

import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.ObjectFactory;
import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules;
import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules.Rule;

/**
 * <p>
 * This goal generates a "rules.xml" file which can be put in "$SONAR_HOME/conf"
 * directory of Sonarqube.
 * </p>
 * <p>
 * This will enable the import of custom rules in Sonarqube.
 * </p>
 *
 * @author Mathieu Debove
 *
 */
@Mojo (name = "generate-sonar-rules", requiresProject = false)
@Execute(goal = "generate-sonar-rules", lifecycle = "sonar", phase = LifecyclePhase.TEST)
public class ConvertRulesToSonarMojo extends CodeReviewNoForkMojo {

	@Parameter(property="bw.review.rules.path", defaultValue="/CodeReview/Processes/Review")
	protected String rulesPath;

	@Parameter(property="bw.review.rules.recursive", defaultValue="false")
	protected Boolean recursive;

	@Parameter(property="bw.review.rules.pattern", defaultValue="[A-Z]{3}-(.*)\\.process")
	protected String rulesPattern;

	@Parameter(property="bw.review.rules.output", defaultValue="${project.review.directory}/rules.xml")
	protected File rulesOutput;

	@Parameter(property="session", required=true, readonly=true)
	protected MavenSession session;

	public void save(File f, Rules rules) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(rules, f);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void processRules(Collection<File> rulesFiles) throws SAXException, IOException {
		Rules rules = new Rules();

		for (File ruleFile : rulesFiles) {
			getLog().debug(ruleFile.getAbsolutePath());

			Match document = JOOX.$(ruleFile);
			document = document.namespace("pd", "http://xmlns.tibco.com/bw/process/2003");
			document = document.namespace("rule", "http://www.fastconnect.fr/FCTibcoFactory/CodeReview/RuleCard.xsd");
			document = document.namespace("xsl", "http://www.w3.org/1999/XSL/Transform");

			Match rule = document.xpath("//rule:rule");
			String ruleKey = rule.xpath("rule:key/xsl:value-of").attr("select");
			String ruleName = rule.xpath("rule:infos/rule:name/xsl:value-of").attr("select"); // TODO: handle i18n
			String ruleSeverity = rule.xpath("rule:severity/xsl:value-of").attr("select");
			String ruleDescription = rule.xpath("rule:infos/rule:description/xsl:value-of").attr("select"); // TODO: handle i18n

			if (ruleKey != null) {
				ruleKey = ruleKey.replaceAll("^\"|\"$", "");
				ruleName = ruleName.replaceAll("^\"|\"$", "");
				ruleSeverity = ruleSeverity.replaceAll("^\"|\"$", "");
				ruleDescription = ruleDescription.replaceAll("^\"|\"$", "");

				getLog().debug(ruleKey);
				getLog().debug(ruleName);
				getLog().debug(ruleSeverity);
				getLog().debug(ruleDescription);

				Rule r = new Rule();
				r.setKey(ruleKey);
				r.setName(ruleName);
				r.setSeverity(ruleSeverity);
				r.setDescription(ruleDescription);

				rules.getRule().add(r);
			}
		}

		rulesOutput.getParentFile().mkdirs();
		save(rulesOutput, rules);
		getLog().info("Sonar rules saved to " + rulesOutput.getAbsolutePath());
	}

	@Override
	public void execute() throws MojoExecutionException {
//		super.execute(); // don't call or it will call the Service

		if (!codeReviewProjectDirectory.exists()) {
			getLog().error("Reviewer project not found. Skipping.");
			throw new MojoExecutionException("Reviewer project not found. Skipping.");
		}

		File rulesDirectory = new File(codeReviewProjectDirectory, rulesPath);

		if (!rulesDirectory.exists()) {
			getLog().error("Rules directory not found in reviewer project not found. Skipping.");
			throw new MojoExecutionException("Rules directory not found in reviewer project not found. Skipping.");
		}

		Collection<File> rules = FileUtils.listFiles(rulesDirectory, new RegexFileFilter(rulesPattern), FileFilterUtils.directoryFileFilter());
		try {
			processRules(rules);
		} catch (SAXException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}
	}

}
