package fr.fastconnect.factory.tibco.bw.codereview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.types.Token;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.CodeReview;
import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.DisabledRules;
import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format;
import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Formats;
import fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Language;
import fr.fastconnect.factory.tibco.bw.maven.bwengine.AbstractServiceEngineMojo;

/**
 * <p>
 * This goal will execute the Code Review on the target project.
 * </p>
 */
@Mojo(name = "code-review", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class CodeReviewLauncherMojo extends AbstractServiceEngineMojo {

	/**
	 * Port of the Code Review WebService in the TIBCO BWEngine running the
	 * tests.
	 */
	@Parameter(property = "codereview.engine.port", required = true, defaultValue = "9099")
	protected String bwEnginePort;

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

	protected final static String CODE_REVIEW_ERROR_NO_BWPROJECT = "The required BW project for reviewing is not available.";
	protected final static String CODE_REVIEW_WARNING_NO_RESOURCES = "The display resources (CSS, images...) are not available.";

	/**
	 * Path to the CodeReview BusinessWorks project.
	 * 
	 */
	@Parameter(property = "project.review.project.directory", defaultValue = "${project.review.directory}/project")
	protected File codeReviewProjectDirectory;

	/**
	 * Path to the CodeReview BusinessWorks project.
	 * 
	 */
	@Parameter(property = "codereview.project.name", defaultValue = "BWRules")
	protected String codeReviewProjectName;

	protected final static String outputDirectoryProperty = "project.review.directory";

	/**
	 * Path to the output directory of the Code Review.
	 * 
	 */
	@Parameter(property = outputDirectoryProperty, defaultValue = "${project.build.directory}/review")
	protected File outputDirectory;

	/**
	 * Path to display resources files (CSS, images...)
	 * 
	 */
	@Parameter(property = "project.review.resources.directory", defaultValue = "${project.review.directory}/resources")
	protected File displayResourcesDırectory;

	@Parameter(property = "bw.review.skip", defaultValue = "false", required = false)
	protected boolean skipReview;

	/**
	 * This will look for a child directory beginning with {@code childPrefix}
	 * inside the parent directory {@code directoryToLook} and move the content
	 * of this child directory to the parent directory.
	 * 
	 * Ex: directoryToLook = "C:/parent/childdir/content" and childPrefix =
	 * "child" will become "C:/parent/content"
	 * 
	 * @param directoryToLook
	 * @param childPrefix
	 * @throws IOException
	 */
	private boolean moveChildToParent(File directoryToLook,
			final String childPrefix) throws IOException {
		DirectoryFileFilter dff = new DirectoryFileFilter() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean accept(File file) {
				return file.getName().startsWith(childPrefix)
						&& super.accept(file);
			}
		};

		getLog().debug(directoryToLook.getAbsolutePath());
		if (directoryToLook.exists()) {
			List<String> children = Arrays.asList(directoryToLook.list(dff));
			if (children.size() > 0) {
				File realTarget = new File(directoryToLook.getAbsolutePath()
						+ File.separator + children.get(0));

				FileUtils.copyDirectory(realTarget, directoryToLook);
				FileUtils.deleteDirectory(realTarget);
			}
			return true;
		}

		return false;
	}

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

		try {
			if (!moveChildToParent(codeReviewProjectDirectory, "code-review-resources")) {
				throw new MojoExecutionException(CODE_REVIEW_ERROR_NO_BWPROJECT);
			}
			if (!moveChildToParent(displayResourcesDırectory, "code-review-resources")) {
				getLog().warn(CODE_REVIEW_WARNING_NO_RESOURCES);
			}
		} catch (IOException e) {
			throw new MojoExecutionException(CODE_REVIEW_ERROR_NO_BWPROJECT);
		}

		getLog().debug("codeReviewProjectDirectory"	+ codeReviewProjectDirectory.getAbsolutePath());
		getLog().debug("displayResourcesDırectory" + displayResourcesDırectory.getAbsolutePath());

		super.execute();
	}

	@Override
	public void initServiceAgent() throws MojoExecutionException {
		try {
			serviceAgent = new FCCodeReviewService(bwEnginePort);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeServiceMethods() throws MojoExecutionException {
		if (outputFormats == null || outputFormats.isEmpty()) {
			outputFormats = new ArrayList<String>();
			outputFormats.add("html"); // default output format is HTML
		}
		
		List<Format> __formats = new ArrayList<Format>();

		if (outputFormats.contains("html")) {
			__formats.add(Format.HTML);
		}
		if (outputFormats.contains("xml")) {
			__formats.add(Format.XML);
		}
		
		Format[] _formats = new Format[__formats.size()];
		__formats.toArray(_formats);

		Formats formats = new Formats(_formats);

		String aliasesFileName = null;
		File aliasesFile = getAliasesFile();

		if (aliasesFile != null &&
			aliasesFile.exists() &&
			aliasesFile.length() > 0) {
			getLog().debug(aliasesFile.getAbsolutePath());
			aliasesFileName = aliasesFile.getAbsolutePath();
		}

		// Token[] _disabledRules = {new Token("EFF-001")};
		Token[] _disabledRules = {};
		DisabledRules disabledRules = new DisabledRules(_disabledRules);

		File actualSrcDirectory;
		if (buildSrcDirectory != null && buildSrcDirectory.exists()) {
			actualSrcDirectory = buildSrcDirectory;
		} else {
			actualSrcDirectory = projectDirectory;
		}

		CodeReview settings = new CodeReview(actualSrcDirectory.getAbsolutePath(),
											 outputDirectory.getAbsolutePath(),
											 sourceEncoding,
											 getProject().getName(),
											 formats,
											 aliasesFileName,
											 Language.fromString(lang), disabledRules);

		// exécution des tests en appelant la méthode "review" du Service Agent de CodeReview
		if (((FCCodeReviewService) serviceAgent).review(settings)) {
			getLog().info(CODE_REVIEW_SUCCESSFUL);
			getLog().info(CODE_REVIEW_RESULTS + "\"" + outputDirectory.getAbsolutePath() + "\"");
		}
	}

	@Override
	public String getBWEnginePort() {
		return bwEnginePort;
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
		return new File(codeReviewProjectDirectory + File.separator
				+ codeReviewProjectName);
	}

}
