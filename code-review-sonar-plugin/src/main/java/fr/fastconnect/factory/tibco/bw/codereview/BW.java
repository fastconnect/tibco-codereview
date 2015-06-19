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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import com.google.common.collect.Lists;

/**
 *
 * @author Mathieu Debove
 *
 */
public class BW extends AbstractLanguage {

	/**
	 * BW key
	 */
	public static final String KEY = "bw";

	/**
	 * BW name
	 */
	public static final String NAME = "BW";

	/**
	 * Key of the file suffix parameter
	 */
	public static final String FILE_SUFFIXES_KEY = "sonar.bw.file.suffixes";

	/**
	 * Default Java files knows suffixes
	 */
	public static final String DEFAULT_FILE_SUFFIXES = ".process";

	/**
	 * Settings of the plugin.
	 */
	private final Settings settings;

	/**
	 * Default constructor
	 */
	public BW(Settings settings) {
		super(KEY, NAME);
		this.settings = settings;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.sonar.api.resources.AbstractLanguage#getFileSuffixes()
	 */
	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = filterEmptyStrings(settings.getStringArray(BW.FILE_SUFFIXES_KEY));
		if (suffixes.length == 0) {
			suffixes = StringUtils.split(DEFAULT_FILE_SUFFIXES, ",");
		}
		return suffixes;
	}

	private static String[] filterEmptyStrings(String[] stringArray) {
		List<String> nonEmptyStrings = Lists.newArrayList();
		for (String string : stringArray) {
			if (StringUtils.isNotBlank(string.trim())) {
				nonEmptyStrings.add(string.trim());
			}
		}
		return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
	}

}
