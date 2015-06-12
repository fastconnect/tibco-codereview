package fr.fastconnect.factory.tibco.bw.codereview.builtin;

import static org.twdata.maven.mojoexecutor.MojoExecutor.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor.Element;

import com.github.goldin.plugins.copy.CopyDependency;
import com.github.goldin.plugins.copy.CopyResource;

import fr.fastconnect.factory.tibco.bw.codereview.CodeReviewLauncherMojo;
import fr.fastconnect.factory.tibco.bw.maven.builtin.AbstractWrapperForBuiltinMojo;

/**
 * <p>
 * Please refer to {@link AbstractWrapperForBuiltinMojo} for a full explanation
 * of the lifecycle binding of a builtin Maven plugin (= builtin Maven Mojo).
 * </p>
 * 
 * <p>
 * <u>Original goal</u> :
 * <b>org.apache.maven.plugins:maven-dependency-plugin:unpack-dependencies</b>
 * </p>
 * 
 * @goal copy-display-resources
 * @phase prepare-package
 * @inheritByDefault true
 * @requiresProject true
 * @aggregator true
 * @requiresDependencyResolution test
 *
 * @author Mathieu Debove
 * 
 */
public class CopyCodeReviewDisplayDependenciesMojo extends AbstractWrapperForBuiltinMojo<CopyResource> {
	// Mojo configuration
	/**
	 *  @parameter property="groupId"
	 */
	protected String groupId;
	
	@Override
	protected String getGroupId() {
		return groupId;
	}

	/**
	 *  @parameter property="artifactId"
	 */
	protected String artifactId;
	
	@Override
	protected String getArtifactId() {
		return artifactId;
	}
	/**
	 *  @parameter property="version"
	 */
	protected String version;
	
	@Override
	protected String getVersion() {
		return version;
	}

	/**
	 *  @parameter property="goal"
	 */
	protected String goal;
	
	@Override
	protected String getGoal() {
		return goal;
	}

	// Environment configuration
	/**
	 * The project currently being build.
	 *
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	@Override
	protected MavenProject getProject() {
		return project;
	}

	/**
	 * The current Maven session.
	 *
	 * @parameter property="session"
	 * @required
	 * @readonly
	 */
	protected MavenSession session;

	@Override
	protected MavenSession getSession() {
		return session;
	}
	/**
	 * The Build Plugin Manager (this one is Java5 annotation style).
	 */
	@Component (role = BuildPluginManager.class)
	protected BuildPluginManager pluginManager;
	
	@Override
	protected BuildPluginManager getPluginManager() {
		return pluginManager;
	}

	// Configuration
    /**
     * The actual Mojo configuration found in the Plexus 'components.xml' file.
     * <pre>
 	 *	&lt;component>
 	 *		&lt;role>org.apache.maven.plugin.Mojo&lt;/role>
 	 *		&lt;role-hint>default-copy-bw-dependencies&lt;/role-hint>
	 *		&lt;implementation>fr.fastconnect.factory.tibco.bw.maven.builtin.CopyBWDependenciesMojo&lt;/implementation>
	 *		&lt;isolated-realm>false&lt;/isolated-realm>
	 *		&lt;configuration>
	 *			&lt;groupId>org.apache.maven.plugins&lt;/groupId>
 	 *			&lt;artifactId>maven-dependency-plugin&lt;/artifactId>
	 *			&lt;version>2.8&lt;/version>
	 *			&lt;goal>copy-dependencies&lt;/goal>
 	 *			&lt;configuration>
 	 *				&lt;property>
 	 *					&lt;name>outputDirectory&lt;/name>
 	 *					&lt;value>${project.build.directory}/lib&lt;/value>
 	 *				&lt;/property>
 	 *				&lt;property>
 	 *					&lt;name>includeTypes&lt;/name>
 	 *					&lt;value>projlib,jar&lt;/value>
 	 *				&lt;/property>
 	 *				&lt;property>
 	 *					&lt;name>includeScope&lt;/name>
 	 *					&lt;value>runtime&lt;/value>
 	 *				&lt;/property>
 	 *				&lt;property>
 	 *					&lt;name>overWriteIfNewer&lt;/name>
 	 *					&lt;value>true&lt;/value>
 	 *				&lt;/property>
 	 *			&lt;/configuration>
	 *		&lt;/configuration>
 	 *		&lt;requirements>
     *			&lt;requirement>
	 *				&lt;role>org.apache.maven.plugin.BuildPluginManager&lt;/role>
     *				&lt;role-hint />
     *				&lt;field-name>pluginManager&lt;/field-name>
     *			&lt;/requirement>
     *		&lt;/requirements>
	 *	&lt;/component>
	 * </pre>
     * @parameter
     */
    protected Properties configuration;

	@Override
	protected Properties getConfiguration() {
		return configuration;
	}

    /**
    * Optional resources parameter do define includes/excludes filesets
    * 
    * @parameter
    */
    protected List<CopyResource> resources;
    
	@Override
	protected List<CopyResource> getResources() {
		return resources;
	}
	
	@Override
	protected List<Element> generateCustomElements(CopyResource resource) {
		List<Element> children = new ArrayList<Element>();
		
		List<Element> dependencies = new ArrayList<Element>();
		for (CopyDependency d : resource.getDependencies()) {
			List<Element> coordinates = new ArrayList<Element>();
			Element groupId = new Element("groupId", d.getGroupId());
			Element artifactId = new Element("artifactId", d.getArtifactId());
			coordinates.add(groupId);
			coordinates.add(artifactId);
			
			Element dependency = new Element("dependency", coordinates.toArray(new Element[0]));
			dependencies.add(element("dependency", dependency));
		}
		children.add(element("dependencies", dependencies.toArray(new Element[0])));

		return children;
	}

    /**
    * 
    * @parameter property="bw.review.skip" default-value="false"
    */
	protected boolean skipReview;

	public boolean skip() {
		return CodeReviewLauncherMojo.skip(this.getProject()) || 
			   skipReview;
	}

	protected final static String SKIPPING = "Skipping.";

	@Override
	public void execute() throws MojoExecutionException {
		if (skip()) {
			getLog().info(SKIPPING);
			return;
		}
		super.execute();
	}

}
