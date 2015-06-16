package fr.fastconnect.factory.tibco.bw.codereview;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.bootstrap.ProjectBuilder;
import org.sonar.api.batch.bootstrap.ProjectDefinition;
import org.sonar.api.config.Settings;

public class BWProjectBuilder extends ProjectBuilder {

	private static final Logger logger = LoggerFactory.getLogger(BWProjectBuilder.class);

	private Settings settings;

	public BWProjectBuilder(Settings settings) {
		this.settings = settings;
	}

	private void addSources(ProjectDefinition project) {
		final File basedir = project.getBaseDir();

		// TODO: ignore child modules folders more properly
		IOFileFilter custom = new IOFileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isDirectory()
						&& !(new File(file, "pom.xml").exists())
						|| file.getAbsolutePath().equals(
								basedir.getAbsolutePath());
			}

			@Override
			public boolean accept(File dir, String name) {
				return false;
			}
		};

		Collection<File> files = FileUtils.listFiles(basedir,
				new SuffixFileFilter(".process"), new AndFileFilter(
						new NotFileFilter(new PrefixFileFilter("target")),
						custom));

		project.addSources(files.toArray(new File[0]));
	}

	@Override
	public void build(Context context) {
		// if (!settings.getBoolean("fc.codereview.enableProjectBuilder")) {
		// return;
		// }
		for (ProjectDefinition project : context.projectReactor().getProjects()) {
			addSources(project);
		}
	}
}
