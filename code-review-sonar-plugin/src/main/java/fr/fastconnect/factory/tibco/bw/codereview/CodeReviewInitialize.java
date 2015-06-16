package fr.fastconnect.factory.tibco.bw.codereview;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Initializer;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import com.google.common.collect.Lists;

import fr.fastconnect.factory.tibco.bw.codereview.jaxb.ObjectFactory;
import fr.fastconnect.factory.tibco.bw.codereview.jaxb.ReviewResult;

public class CodeReviewInitialize extends Initializer {

	private static final Logger logger = LoggerFactory.getLogger(CodeReviewInitialize.class);

	private String relativePath;
	private FileSystem fs;

	public static ReviewResult reviewResult;

	public CodeReviewInitialize(Settings settings, FileSystem fs) {
		super();

		this.relativePath = settings.getString(BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH);
		this.fs = fs;
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

	public File getXMLCodeReviewResult() {
		if (this.fs == null || this.relativePath == null) return null;

		File codeReviewPath = new File(fs.baseDir(), this.relativePath);

		return getTheNewestFile(codeReviewPath, "xml");
	}

	@Override
	public void execute(Project project) {
		logger.info("Launching FC Code Review analysis...");
		logger.info(BWCodeReviewPlugin.FC_CODE_REVIEW_RESULTS_RELATIVE_PATH + "=" + relativePath);
		logger.info("Project path=" + fs.baseDir().getAbsolutePath());

		File xmlCodeReview = getXMLCodeReviewResult();
		if (xmlCodeReview != null && xmlCodeReview.exists()) {
			logger.info("Code Review XML result=" + xmlCodeReview.getAbsolutePath());
			reviewResult = loadReviewResult(xmlCodeReview);
			if (reviewResult == null) {
				logger.info("Code Review XML result=" + xmlCodeReview.getAbsolutePath());
			} else {
				//next step
				logger.info(reviewResult.getDuration().toString());
			}
		} else {
			logger.info("The Code Review XML result for this BW project was not found. Skipping.");
		}
	}

	private ReviewResult loadReviewResult(File f) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Object o =  jaxbUnmarshaller.unmarshal(f);
			return (ReviewResult) o;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
