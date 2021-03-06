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
package fr.fastconnect.factory.tibco.bw.codereview.ui;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;

@UserRole(UserRole.USER)
@Description("Show how to use Ruby Widget API")
@WidgetCategory("Sample")
@WidgetProperties({
		@WidgetProperty(key = "param1", description = "This is a mandatory parameter", optional = false),
		@WidgetProperty(key = "max", description = "max threshold", type = WidgetPropertyType.INTEGER, defaultValue = "80"),
		@WidgetProperty(key = "param2", description = "This is an optional parameter"),
		@WidgetProperty(key = "floatprop", description = "test description") })
public class ExampleRubyWidget extends AbstractRubyTemplate implements
		RubyRailsWidget {

	public String getId() {
		return "sample";
	}

	public String getTitle() {
		return "Sample";
	}

	@Override
	protected String getTemplatePath() {
		return "/example/example_widget.html.erb";
	}
}
