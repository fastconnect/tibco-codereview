package fr.fastconnect.factory.tibco.bw.codereview;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.joox.JOOX;
import org.joox.Match;
import org.xml.sax.SAXException;

import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.ObjectFactory;
import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules;
import fr.fastconnect.factory.tibco.bw.codereview.sonar.jaxb.Rules.Rule;

@Mojo(name="convert-rules-to-sonar", requiresProject=false)
public class ConvertRulesToSonar extends AbstractMojo {

	@Parameter(property="bw.review.project.path", defaultValue="../code-review-resources/src/main/resources/bw")
	protected String reviewProjectPath;

	@Parameter(property="bw.review.rules.path", defaultValue="/BWRules/CodeReview/Processes/Review")
	protected String rulesPath;

	@Parameter(property="bw.review.rules.recursive", defaultValue="false")
	protected Boolean recursive;

	@Parameter(property="bw.review.rules.pattern", defaultValue="[A-Z]{3}-(.*)\\.process")
	protected String rulesPattern;

	@Parameter(property="bw.review.rules.output", defaultValue="${basedir}/target/rules.xml")
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

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		File reviewerProject = new File(reviewProjectPath);
		
		if (!reviewerProject.exists()) {
			reviewerProject = new File(session.getRequest().getBaseDirectory(), reviewProjectPath);
			reviewerProject = new File(FilenameUtils.normalize(reviewerProject.getAbsolutePath()));
		}

		if (!reviewerProject.exists()) {
			getLog().info("Reviewer project not found. Skipping.");
			return;
		}

		File rulesDirectory = new File(reviewerProject, rulesPath);

		if (!rulesDirectory.exists()) {
			getLog().info("Rules directory not found in reviewer project not found. Skipping.");
			return;
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

	private void processRules(Collection<File> rulesFiles) throws SAXException, IOException {
		Rules rules = new Rules();

		for (File ruleFile : rulesFiles) {
			getLog().info(ruleFile.getAbsolutePath());

			Match document = JOOX.$(ruleFile);
			document = document.namespace("pd", "http://xmlns.tibco.com/bw/process/2003");
			document = document.namespace("rule", "http://www.fastconnect.fr/FCTibcoFactory/CodeReview/RuleCard.xsd");
			document = document.namespace("xsl", "http://www.w3.org/1999/XSL/Transform");

			Match rule = document.xpath("//rule:rule");
			String ruleKey = rule.xpath("rule:key/xsl:value-of").attr("select");
			String ruleName = rule.xpath("rule:name/xsl:value-of").attr("select");
			String ruleSeverity = rule.xpath("rule:severity/xsl:value-of").attr("select");
			String ruleDescription = rule.xpath("rule:description/xsl:value-of").attr("select");

			if (ruleKey != null) {
				ruleKey = ruleKey.replaceAll("^\"|\"$", "");
				ruleName = ruleName.replaceAll("^\"|\"$", "");
				ruleSeverity = ruleSeverity.replaceAll("^\"|\"$", "");
				ruleDescription = ruleDescription.replaceAll("^\"|\"$", "");

				getLog().info(ruleKey);
				getLog().info(ruleName);
				getLog().info(ruleSeverity);
				getLog().info(ruleDescription);

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
	}

}
