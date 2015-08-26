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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.resolvers.ResolveDependenciesMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.mojo.truezip.TrueZipFileSet;
import org.codehaus.mojo.truezip.internal.DefaultTrueZip;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import de.schlichtherle.truezip.file.TArchiveDetector;
import de.schlichtherle.truezip.file.TConfig;
import de.schlichtherle.truezip.fs.archive.zip.ZipDriver;
import de.schlichtherle.truezip.socket.sl.IOPoolLocator;

/**
 * <p>
 * This goal will execute the Code Review on the target project.
 * </p>
 */
@Mojo(name = "prepare-code-review", defaultPhase = LifecyclePhase.PROCESS_TEST_RESOURCES, requiresProject = true, requiresDependencyResolution = ResolutionScope.TEST)
public class PrepareCodeReviewLauncherMojo extends CodeReviewNoForkMojo {

	@Parameter (property = "code.review.artifactFilter", defaultValue = "code-review-")
	private String artifactIdPrefixFilter;

	@Parameter (property = "code.review.project.artifactId", defaultValue = "code-review-reviewer-project")
	private String reviewerProjectArtifactId;

    protected class ResolveDependenciesWithProjectMojo extends ResolveDependenciesMojo {

		public void setProject(MavenProject aProject) {
			this.project = aProject;
			this.silent = true;
		}
    }

    protected ArrayList<Artifact> getDisplayResources() throws MojoExecutionException {
		Predicate<Artifact> predicate = new Predicate<Artifact>() {
			@Override
			public boolean apply(Artifact a) {
				return "zip".equals(a.getType()) && "display".equals(a.getClassifier()) && a.getArtifactId().startsWith(artifactIdPrefixFilter);
			}
		};

		return getDependencies(predicate);
    }

	protected ArrayList<Artifact> getRules() throws MojoExecutionException {
		Predicate<Artifact> predicate = new Predicate<Artifact>() {
			@Override
			public boolean apply(Artifact a) {
				return "zip".equals(a.getType()) && "bw".equals(a.getClassifier()) && a.getArtifactId().startsWith(artifactIdPrefixFilter);
			}
		};

		return getDependencies(predicate);
	}

	protected ArrayList<Artifact> getDependencies(Predicate<Artifact> predicate) throws MojoExecutionException {
		ResolveDependenciesWithProjectMojo rdm = new ResolveDependenciesWithProjectMojo();
		rdm.setProject(getProject());
		rdm.execute();
		Set<Artifact> artifacts = rdm.getResults().getResolvedDependencies();

		if (artifacts != null && !artifacts.isEmpty()) {
			return Lists.newArrayList(Collections2.filter(artifacts, predicate));
		} else {
			return new ArrayList<Artifact>();
		}
	}

	private boolean extract(String from, String to) throws IOException {
		TrueZipFileSet fileSet = new TrueZipFileSet();
		fileSet.setDirectory(from);
		fileSet.setOutputDirectory(to);
		fileSet.addInclude("**/*");
		fileSet.setFollowArchive(true);

		DefaultTrueZip truezip = new DefaultTrueZip();
		truezip.copy(fileSet);
		truezip.sync();

		return true;
	}

	@Override
	public void execute() throws MojoExecutionException {
		if (skip()) {
			getLog().info(SKIPPING);
			return;
		}

//		super.execute(); // don't call or it will call the Service
		codeReviewProjectDirectory.mkdirs();

		TConfig.get().setArchiveDetector( new TArchiveDetector( TArchiveDetector.NULL, new Object[][] {
				{ "zip", new ZipDriver( IOPoolLocator.SINGLETON ) },
		} ) );

		List<Artifact> rulesProjects = getRules();

		boolean reviewerProjectFound = false;
		// begin with the reviewer project (main one)
		for (Artifact artifact : rulesProjects) {
			if (artifact.getArtifactId().equals(reviewerProjectArtifactId)) {
				try {
					extract(artifact.getFile().getAbsolutePath() + "/" + artifact.getArtifactId() + "-" + artifact.getVersion(), codeReviewProjectDirectory.getAbsolutePath());
				} catch (IOException e) {
					throw new MojoExecutionException(e.getLocalizedMessage(), e);
				}
				reviewerProjectFound = true;
			}
		}

		if (!reviewerProjectFound) {
			getLog().error(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT);
			getLog().error(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT_HOWTO);
			throw new MojoExecutionException(CodeReviewNoForkMojo.CODE_REVIEW_ERROR_NO_BWPROJECT);
		}

		// then add all the rules projects
		for (Artifact artifact : rulesProjects) {
			if (!artifact.getArtifactId().equals(reviewerProjectArtifactId)) {
				try {
					extract(artifact.getFile().getAbsolutePath() + "/" + artifact.getArtifactId() + "-" + artifact.getVersion(), codeReviewProjectDirectory.getAbsolutePath());
				} catch (IOException e) {
					throw new MojoExecutionException(e.getLocalizedMessage(), e);
				}
			}
		}

		// last the display resources
		ArrayList<Artifact> displayResources = getDisplayResources();
		if (displayResources.isEmpty()) {
			getLog().warn(CodeReviewNoForkMojo.CODE_REVIEW_WARNING_NO_RESOURCES);
		}
		for (Artifact artifact : displayResources) {
			try {
				extract(artifact.getFile().getAbsolutePath() + "/" + artifact.getArtifactId() + "-" + artifact.getVersion(), displayResourcesDırectory.getAbsolutePath());
			} catch (IOException e) {
				throw new MojoExecutionException(e.getLocalizedMessage(), e);
			}
		}
	}

}
