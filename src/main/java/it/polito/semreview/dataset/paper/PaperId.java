package it.polito.semreview.dataset.paper;

import com.google.common.base.Preconditions;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Paper is identified by the journal name, the year, the number of the issue and the title of the paper.
 */
public class PaperId implements Serializable {

	private static final long serialVersionUID = -4247736922068907775L;
	
	private final String journalName;
	private final int year;
	private final int issue;
    private final String title;

    /**
     *
     * @param journalName
     * @param year it is expected to be > 1000
     * @param issue it is expected to be > 0
     * @param title
     */
	public PaperId(@NotNull String journalName, int year, int issue, @NotNull String title) {
        Preconditions.checkNotNull(journalName, "JournalName should be not empty");
        Preconditions.checkNotNull(title, "Title should be not empty");
        Preconditions.checkArgument(year > 1000, "The year of publication should be greater then 1000");
        Preconditions.checkArgument(issue > 0, "The issue should be greater then 0");
		this.journalName = journalName;
		this.year = year;
		this.issue = issue;
		this.title = title.toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + issue;
		result = prime * result
				+ ((journalName == null) ? 0 : journalName.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public String toString() {
		return "PaperId [journalName=" + journalName + ", year=" + year
				+ ", issue=" + issue + ", title=" + title + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaperId other = (PaperId) obj;
		if (issue != other.issue)
			return false;
		if (journalName == null) {
			if (other.journalName != null)
				return false;
		} else if (!journalName.equals(other.journalName))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public @NotNull String getJournalName() {
		return journalName;
	}

	public int getYear() {
		return year;
	}

	public int getIssue() {
		return issue;
	}

	public @NotNull String getTitle() {
		return title;
	}
	
}
