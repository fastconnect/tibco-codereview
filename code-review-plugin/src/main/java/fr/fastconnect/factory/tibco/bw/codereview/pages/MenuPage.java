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
package fr.fastconnect.factory.tibco.bw.codereview.pages;

import static org.rendersnake.HtmlAttributesFactory.href;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import fr.fastconnect.factory.tibco.bw.codereview.CodeReviewNoForkMojo;

/**
 * <p>
 * This creates the menu.
 * </p>
 */
public class MenuPage extends CodeReviewNoForkMojo implements Renderable {

	private String menuTitle;
	private MavenSession session;
	private String projectNameProperty;

	public MenuPage(String menuTitle, MavenSession session, String projectNameProperty) {
		this.menuTitle = menuTitle;
		this.session = session;
		this.projectNameProperty = projectNameProperty;
	}

	@Override
	public void renderOn(HtmlCanvas html) throws IOException {
		html
			.html()
	  			.render(new Header())
	  			.body()
					.render(new Menu(session, projectNameProperty))
				._body()
	  		._html();
	}

	public File getTheNewestFile(File directory, String extension) {
	    File newestFile = null;
	    if (directory == null || !directory.exists() || !directory.isDirectory()) {
	    	return newestFile;
	    }

	    FileFilter fileFilter = new WildcardFileFilter("*." + extension);
	    File[] files = directory.listFiles(fileFilter);

	    if (files.length > 0) {
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        newestFile = files[0];
	    }

	    return newestFile;
	}

/**
 * HTML elements
 */

	public class Header implements Renderable {

		@Override
		public void renderOn(HtmlCanvas html) throws IOException {
		    html
		    	.head()
		    		.title().content(menuTitle)
		    	._head();           
		   }
	}

	public class ProjectElement implements Renderable {
		private MavenProject project;
		private String projectNameProperty;
		
		public ProjectElement(MavenProject project, String projectNameProperty) {
			this.project = project;
			this.projectNameProperty = projectNameProperty;
		}

		@Override
		public void renderOn(HtmlCanvas html) throws IOException {
			String projectName;
			if ("project.name".equals(projectNameProperty)) {
				projectName = project.getName();
			} else {
				projectName = project.getProperties().getProperty(projectNameProperty);
			}
			String outputDirectory = project.getProperties().getProperty(outputDirectoryProperty);
			File projectLink = getTheNewestFile(new File(outputDirectory), "html");

			if (projectLink != null) {
				html.li().a(href(projectLink.getName()).target("content")).write(projectName)._a()._li();
			}
		}
		
	}

	public class Menu implements Renderable {
		
		private MavenSession session;
		private String projectNameProperty;

		public Menu(MavenSession session, String projectNameProperty) {
			this.session = session;
			this.projectNameProperty = projectNameProperty;
		}

		@Override
		public void renderOn(HtmlCanvas html) throws IOException {
			html.ul();
			
			for (MavenProject project : session.getProjects()) {
				if (BWEAR_TYPE.equals(project.getPackaging()) || PROJLIB_TYPE.equals(project.getPackaging())) {
					html.render(new ProjectElement(project, projectNameProperty));
				}
			}
			
			html._ul();
		}
	}

}
