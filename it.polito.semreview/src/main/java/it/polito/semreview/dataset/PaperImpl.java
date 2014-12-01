package it.polito.semreview.dataset;

import it.polito.softeng.common.exceptions.UnknownElementException;

import java.util.HashMap;
import java.util.Map;

public class PaperImpl implements Paper {
	
	private PaperId paperId;
	private Map<String,String> sections = new HashMap<String,String>();
	
	public PaperImpl(PaperId paperId) {
		super();
		this.paperId = paperId;
	}
	@Override
	public PaperId getId() {
		return paperId;
	}
	@Override
	public String collateText() {
		StringBuffer buffer = new StringBuffer();
		
		for (String sectionText : sections.values()){
			buffer.append(sectionText);
		}
		
		return buffer.toString();
	}
	public void addSection(String sectionName, String sectionContent){
		sections.put(sectionName, sectionContent);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paperId == null) ? 0 : paperId.hashCode());
		result = prime * result
				+ ((sections == null) ? 0 : sections.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaperImpl other = (PaperImpl) obj;
		if (paperId == null) {
			if (other.paperId != null)
				return false;
		} else if (!paperId.equals(other.paperId))
			return false;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
			return false;
		return true;
	}
	@Override
	public String[] getSectionNames() {
		return sections.keySet().toArray(new String[]{});
	}
	@Override
	public String getSectionText(String sectionName) {
		if (!sections.containsKey(sectionName)){
			throw new UnknownElementException(sectionName);
		}
		return sections.get(sectionName);
	}


}
