package it.polito.semreview.dataset.paper;

import it.polito.softeng.common.collections.Pair;
import it.polito.softeng.common.StringUtils;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.observerpattern.ObservableImpl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This class examine a directory and its subdirectories files related to paper.
 */
public abstract class PapersDirLoadingStrategy<T extends Serializable> extends ObservableImpl<Pair<PaperId,T>> {
	
	private static Logger logger = Logger.getLogger(PapersDirLoadingStrategy.class);

	private final File rootDir;
	private final String journalName;
	private final String extension;

	public PapersDirLoadingStrategy(File rootDir, String journalName, String extension) {
		if (!rootDir.exists() || !rootDir.isDirectory()) {
			throw new IllegalArgumentException(
					"Given path should exist and being a directory");
		}
		this.rootDir = rootDir;
		this.journalName = journalName;
		this.extension = extension;
	}

	protected abstract T load(PaperId paperId, File file) throws LoadingException;
	
	public Map<PaperId,T> getAll() throws IOException, LoadingException {
		Map<PaperId,T> papers = new HashMap<>();
		int yearsCount = 0;
		int issuesCount = 0;
		int papersCount = 0;
		for (File yearDir : rootDir.listFiles()) {
			try {
				int year = Integer.parseInt(yearDir.getName());
				yearsCount++;
				for (File issueDir : yearDir.listFiles()) {
					if (issueDir.isDirectory()
							&& issueDir.getName().startsWith("issue_")) {
						try {
							int issue = Integer.parseInt(issueDir.getName()
									.substring("issue_".length()));
							for (File paperFile : issueDir.listFiles()) {
								if (paperFile.isFile()&&paperFile.getName().endsWith("."+extension)) {
									String title = paperFile.getName();
									title = StringUtils.removePostfix(title,
											"."+extension);
									PaperId paperId = new PaperId(
											this.journalName, year, issue,
											title);
									T t = load(paperId, paperFile);
									notifyEvent(new Pair<>(paperId, t));
									papers.put(paperId,t);
									papersCount++;
								}
							}
							issuesCount++;
							logger.debug("Year " + year + ", issue "
									 + issue+" (issues done: "+issuesCount+")");
						} catch (NumberFormatException e) {
							logger.debug("...skipping "
									+ issueDir.getAbsolutePath());
						}

					} else {
						logger.debug("...skipping "
								+ issueDir.getAbsolutePath());
					}
				}
			} catch (NumberFormatException e) {
				// skip the subdir
			}
		}
		logger.debug("Years: " + yearsCount + ", issues " + issuesCount+", papers "+papersCount);
		return papers;
	}

}
