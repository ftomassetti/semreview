package it.polito.semreview.dataset;

public class PaperId {

	private String journalName;
	private int year;
	private int issue;
	public PaperId(String journalName, int year, int issue, String title) {
		super();
		this.journalName = journalName;
		this.year = year;
		this.issue = issue;
		this.title = title;
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
	private String title;
	public String getJournalName() {
		return journalName;
	}
	public int getYear() {
		return year;
	}
	public int getIssue() {
		return issue;
	}
	public String getTitle() {
		return title;
	}
	
}
