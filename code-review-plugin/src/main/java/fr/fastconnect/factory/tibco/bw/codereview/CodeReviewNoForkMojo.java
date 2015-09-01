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
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import fr.fastconnect.factory.tibco.bw.codereview.jaxws.CodeReview_Type;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.DisabledRules;
import fr.fastconnect.factory.tibco.bw.codereview.jaxws.Formats;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.AbstractServiceEngineMojo;

/**
 * <p>
 * This goal will execute the Code Review on the target project.
 * </p>
 *
 * @author Mathieu Debove
 *
 */
@Mojo(name = "code-review-no-fork", defaultPhase = LifecyclePhase.TEST)
public class CodeReviewNoForkMojo extends AbstractServiceEngineMojo {

//	/**
//	 * Port of the Code Review WebService in the TIBCO BWEngine running the
//	 * tests.
//	 */
//	@Parameter(property = "codereview.engine.port", required = true, defaultValue = "9099")
//	protected Integer bwEnginePort;

//	protected static Integer uniqueEnginePort = null;

	@Parameter(property = "codereview.timeout", defaultValue = "100")
	private int timeOut; // in seconds

	@Parameter(property = "codereview.retryInterval", defaultValue = "1")
	private int retryInterval; // interval between Code Review WebService call, in seconds

	@Parameter(property = "codereview.language", defaultValue = "FR")
	private String lang; // can be FR or EN / TODO: constraint to available languages

	@Parameter
	private List<String> outputFormats;

	protected final static String CODE_REVIEW_SERVICE_NAME = "CodeReview";
	protected final static String CODE_REVIEW_FAILURE = "The execution of the code review failed";
	protected final static String CODE_REVIEW_TIMEOUT = "The execution of the code review timed out";
	protected final static String CODE_REVIEW_SUCCESSFUL = "The code review was executed successfully";
	protected final static String CODE_REVIEW_RESULTS = "The results are found in : ";

	public final static String CODE_REVIEW_ERROR_NO_BWPROJECT = "The required BW project for reviewing is not available.";
	public final static String CODE_REVIEW_ERROR_NO_BWPROJECT_HOWTO = "Add the \"fr.fastconnect.factory.tibco.bw.codereview:code-review-rules-basic:zip:bw:test\" dependency.";
	public final static String CODE_REVIEW_WARNING_NO_RESOURCES = "The display resources (CSS, images...) are not available.";

	/**
	 * Path to the CodeReview BusinessWorks project.
	 *
	 */
	@Parameter(property = "project.review.project.directory", defaultValue = "${project.review.directory}/project")
	protected File codeReviewProjectDirectory;

	protected final static String outputDirectoryProperty = "project.review.directory";

	/**
	 * Path to the output directory of the Code Review.
	 *
	 */
	@Parameter(property = outputDirectoryProperty, defaultValue = "${project.build.directory}/review")
	protected File outputDirectory;

	/**
	 * <p>
	 * Path to display resources files (CSS, images...)
	 * </p>
	 */
	@Parameter(property = "project.review.resources.directory", defaultValue = "${project.review.directory}/resources")
	protected File displayResourcesDırectory;

	@Parameter(property = "bw.review.skip", defaultValue = "false", required = false)
	protected boolean skipReview;

	private String bwEnginePort;

	public static boolean skip(MavenProject mavenProject) {
		return !BWEAR_TYPE.equals(mavenProject.getPackaging()) &&
			   !PROJLIB_TYPE.equals(mavenProject.getPackaging());
	}

	public boolean skip() {
		return skip(this.getProject()) ||
			   skipReview;
	}

	public void execute() throws MojoExecutionException {
		if (skip()) {
			getLog().info(SKIPPING);
			return;
		}

		getLog().debug("codeReviewProjectDirectory"	+ codeReviewProjectDirectory.getAbsolutePath());
		getLog().debug("displayResourcesDırectory" + displayResourcesDırectory.getAbsolutePath());

		if (codeReviewProjectDirectory == null || !codeReviewProjectDirectory.exists()) {
			getLog().error(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT);
			getLog().error(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT_HOWTO);
			throw new MojoExecutionException(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT);
		}

		super.execute();
	}

	public static boolean available(int port) {
	    if (port < 49152 || port > 65535) {
	        throw new IllegalArgumentException("Invalid start port: " + port);
	    }

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	                /* should not be thrown */
	            }
	        }
	    }

	    return false;
	}

	public static Integer getFreePort() {
		Random rand = new Random();
		int port = -1;
		do {
			port = rand.nextInt((65535 - 49152) + 1) + 49152;
		} while (!available(port));

	    return port;
	}

	@Override
	public void initServiceAgent() throws MojoExecutionException {
		bwEnginePort = getFreePort().toString();

		getLog().debug("engine port : " + bwEnginePort);
		try {
			serviceAgent = new FCCodeReviewService(bwEnginePort.toString());
		} catch (Exception e) {
			throw new MojoExecutionException(CODE_REVIEW_FAILURE, e);
		}
	}

	@Override
	public void executeServiceMethods() throws MojoExecutionException {
		if (outputFormats == null || outputFormats.isEmpty()) {
			outputFormats = new ArrayList<String>();
			outputFormats.add("html"); // default output format is HTML
		}

		Formats formats = new Formats();
		if (outputFormats.contains("html")) {
			formats.getFormat().add("HTML");
		}
		if (outputFormats.contains("xml")) {
			formats.getFormat().add("XML");
		}

		String aliasesFileName = null;
		File aliasesFile = getAliasesFile();

		if (aliasesFile != null &&
			aliasesFile.exists() &&
			aliasesFile.length() > 0) {
			getLog().debug(aliasesFile.getAbsolutePath());
			aliasesFileName = aliasesFile.getAbsolutePath();
		}

		DisabledRules disabledRules = new DisabledRules();
		//disabledRules.getDisabledRule().add("EFF-001")

		File actualSrcDirectory;
		if (buildSrcDirectory != null && buildSrcDirectory.exists()) {
			actualSrcDirectory = buildSrcDirectory;
		} else {
			actualSrcDirectory = projectDirectory;
		}

		CodeReview_Type settings = new CodeReview_Type();
		settings.setProjectPath(actualSrcDirectory.getAbsolutePath());
		settings.setSrcProjectPath(projectDirectory.getAbsolutePath());
		settings.setDestination(outputDirectory.getAbsolutePath());
		settings.setProjectEncoding(sourceEncoding);
		settings.setProjectName(getProject().getName());
		settings.setFormats(formats);
		settings.setFileAliasesFile(aliasesFileName);
		settings.setLanguage(lang);
		settings.setDisabledRules(disabledRules);

		// exécution des tests en appelant la méthode "review" du Service Agent de CodeReview
		if (((FCCodeReviewService) serviceAgent).review(settings)) {
			getLog().info(CODE_REVIEW_SUCCESSFUL);
			getLog().info(CODE_REVIEW_RESULTS + "\"" + outputDirectory.getAbsolutePath() + "\"");
		}
	}

	@Override
	public String getBWEnginePort() {
		return bwEnginePort.toString();
	}

	@Override
	public int getTimeOut() {
		return timeOut;
	}

	@Override
	public int getRetryInterval() {
		return retryInterval;
	}

	@Override
	public String getServiceName() {
		return CODE_REVIEW_SERVICE_NAME;
	}

	@Override
	public String getServiceFailureMessage() {
		return CODE_REVIEW_FAILURE;
	}

	@Override
	public String getServiceTimeoutMessage() {
		return CODE_REVIEW_TIMEOUT;
	}

	@Override
	protected File getProjectToRunPath() {
		return codeReviewProjectDirectory;
	}

}
