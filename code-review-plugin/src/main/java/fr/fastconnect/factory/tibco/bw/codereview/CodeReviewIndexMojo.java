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
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.rendersnake.HtmlCanvas;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

/**
 * <p>
 * This goal creates an index HTML page to navigate in all Code Reviews.
 * </p>
 */
@Mojo(name = "code-review-index", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class CodeReviewIndexMojo extends CodeReviewLauncherMojo {

	@Parameter(defaultValue="index.html")
	protected String indexFileName;

	@Parameter(defaultValue="menu.html")
	protected String menuFileName;

	@Parameter(defaultValue="project.name")
	protected String projectNameProperty;
	
	@Parameter(defaultValue="FC Code Review - List of projects")
	protected String indexTitle;

	/**
	 * Use the parent POM as the source instead of the current POM.
	 * This might be useful for the delivery POM pattern.
	 */
	@Parameter ( property = "generate.pom.use.parent", required = false, defaultValue = "false")
	protected boolean useParent;

	private File createFile(String fileName) throws IOException {
		File file = new File(outputDirectory + File.separator + fileName);
		file.getParentFile().mkdirs();
		file.createNewFile();
		
		return file;
	}

	private File createIndexFile() throws IOException {		
		return createFile(indexFileName);
	}

	private File createMenuFile() throws IOException {		
		return createFile(menuFileName);
	}

	protected String formatHtml(String html) throws MojoExecutionException {
		try {
			InputSource src = new InputSource(new StringReader(html));
			Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
			Boolean keepDeclaration = Boolean.valueOf(html.startsWith("<?xml"));
			
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer writer = impl.createLSSerializer();
			
			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
			
			return writer.writeToString(document);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
    }

	protected void generateIndexFile() throws IOException, MojoExecutionException {
		File indexFile = createIndexFile();

		HtmlCanvas html = new HtmlCanvas();
		html.render(new IndexPage(indexTitle));

		FileUtils.write(indexFile, formatHtml(html.toHtml()), "UTF-8");		
	}

	protected void generateMenuFile() throws IOException, MojoExecutionException {
		File menuFile = createMenuFile();
		
		HtmlCanvas html = new HtmlCanvas();
		html.render(new MenuPage(indexTitle, session, projectNameProperty));
		
		FileUtils.write(menuFile, formatHtml(html.toHtml()), "UTF-8");		
	}

	public static boolean skip(MavenProject mavenProject) {
		return !POM_TYPE.equals(mavenProject.getPackaging());
	}

	public boolean skip() {
		return skip(this.getProject()) || 
			   skipReview;
	}

	@Override
	public void execute() throws MojoExecutionException {
		if (skip()) {
			getLog().info(SKIPPING);
			return;
		}
		try {
			generateIndexFile();
			generateMenuFile();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

}
