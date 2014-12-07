package it.polito.semreview.dataset;

import it.polito.semreview.utils.filesystem.FileUtils;
import it.polito.softeng.common.collections.Pair;
import it.polito.softeng.common.StringUtils;
import it.polito.softeng.common.observerpattern.ObservableImpl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class examine a directory and its subdirectories to find text files
 * which are loaded and Paper are created.
 */
public class TextFileDirDataSetProvider extends ObservableImpl<Pair<PaperId,File>> implements DataSetProvider<Paper> {
	
	private static Logger logger = Logger.getLogger(TextFileDirDataSetProvider.class);

	private File rootDir;
	private String journalName;

	public TextFileDirDataSetProvider(File rootDir, String journalName) {
		if (!rootDir.exists() || !rootDir.isDirectory()) {
			throw new IllegalArgumentException(
					"Given path should exist and being a directory");
		}
		this.rootDir = rootDir;
		this.journalName = journalName;
	}

	@Override
	public List<Paper> getAllDocuments() throws IOException {
		List<Paper> papers = new LinkedList<Paper>();
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
								if (paperFile.isFile()&&paperFile.getName().endsWith(".txt")) {
									String title = paperFile.getName();
									title = StringUtils.removePostfix(title,
											".txt");
									PaperId paperId = new PaperId(
											this.journalName, year, issue,
											title);
									notifyEvent(new Pair<PaperId, File>(paperId,paperFile));
									papers.add(load(paperId, paperFile));
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
		/*
		 * for (File textFile : FileUtils.listFile(rootDir,new
		 * FileNameExtensionFilter("txt"), true)){ papers.add(load(textFile)); }
		 */
		return papers;
	}

	private Paper load(PaperId paperId, File textFile) throws IOException {
		PaperImpl paper = new PaperImpl(paperId);
		String content = FileUtils.readFile(textFile);
		paper.addSection("fullText", content);
		return paper;
	}

}
