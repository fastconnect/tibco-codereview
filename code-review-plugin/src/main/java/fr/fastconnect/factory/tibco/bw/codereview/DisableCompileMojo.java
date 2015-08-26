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

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import fr.fastconnect.factory.tibco.bw.maven.AbstractBWMojo;

/**
 * <p>
 * This goal disables the compilation of Projlib or EAR.
 * </p>
 */
@Mojo(name = "disable-compile", defaultPhase = LifecyclePhase.INITIALIZE, requiresProject = true)
public class DisableCompileMojo extends AbstractBWMojo {

	@Override
	public void execute() throws MojoExecutionException {
		for (Plugin plugin : getProject().getModel().getBuild().getPlugins()) {
			if ("bw-maven-plugin".equals(plugin.getArtifactId())) {
				for (PluginExecution pe : plugin.getExecutions()) {
					if (pe.getGoals().contains("compile-projlib") || pe.getGoals().contains("compile-ear")) {
						Xpp3Dom config = (Xpp3Dom) pe.getConfiguration();
						Xpp3Dom skipCompile = new Xpp3Dom("skipCompile");
						skipCompile.setValue("true");
						config.addChild(skipCompile);
						System.out.println(config.toString());

						getProject().getOriginalModel().setBuild(getProject().getModel().getBuild());
						getProject().getOriginalModel().getProperties().put("bw.compile.skip", true);
						getProject().getModel().getProperties().put("bw.compile.skip", true);
						session.getUserProperties().put("bw.compile.skip", true);
						session.getExecutionProperties().put("bw.compile.skip", true);
					}
				}
			}
		}
		for (MavenProject mavenProject : session.getProjects()) {
			if (mavenProject.getArtifactId().equals(getProject().getArtifactId())) {
				mavenProject = getProject();
			}
		}
	}

}
