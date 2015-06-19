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

import static org.rendersnake.HtmlAttributesFactory.cols;
import static org.rendersnake.HtmlAttributesFactory.href;
import static org.rendersnake.HtmlAttributesFactory.name;

import java.io.IOException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import fr.fastconnect.factory.tibco.bw.maven.AbstractBWMojo;

/**
 * <p>
 * This creates an HTML page with a frameset composed of two frames: a menu and
 * a content.
 * </p>
 */
public class IndexPage extends AbstractBWMojo implements Renderable {

	private String indexTitle;

	public IndexPage(String indexTitle) {
		this.indexTitle = indexTitle;
	}

	@Override
	public void renderOn(HtmlCanvas html) throws IOException {
		html
			.html()
	  			.render(new Header())
					.render(new FrameSet())
	  		._html();
	}

/**
 * HTML elements
 */

	public class Header implements Renderable {

		@Override
		public void renderOn(HtmlCanvas html) throws IOException {
		    html
		    	.head()
		    		.title().content(indexTitle)
		    	._head();           
		   }
	}

	public class FrameSet implements Renderable {

		@Override
		public void renderOn(HtmlCanvas html) throws IOException {
			html
				.frameset(cols("250,*"))
					.frame(name("menu").src("./menu.html"))
					.frame(name("content"))
				._frameset()
			;
		}
		
	}

}
