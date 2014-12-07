package it.polito.semreview.dataset.paper;

import com.google.common.base.Preconditions;
import it.polito.softeng.common.exceptions.UnknownElementException;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class PaperImpl implements Paper {
	
	private final PaperId paperId;
	private final Map<String,String> sections = new HashMap<String,String>();
	
	public PaperImpl(PaperId paperId) {
		this.paperId = paperId;
	}

	@Override
	public PaperId getId() {
		return paperId;
	}

	@Override
	public String collateText() {
		StringBuilder buffer = new StringBuilder();
		
		for (String sectionText : sections.values()){
			buffer.append(sectionText);
		}
		
		return buffer.toString();
	}

    /**
     * Add a section with the given name and content.
     * @param sectionName should be not empty
     * @param sectionContent
     * @return if the section was already existing and it was overriden
     */
	public boolean addSection(@NotNull String sectionName, @NotNull String sectionContent){
        Preconditions.checkNotNull(sectionName, "SectionName should be not null");
        Preconditions.checkNotNull(sectionContent, "SectionContent should be not null");
        Preconditions.checkArgument(!sectionName.isEmpty(), "SectionName should be not empty");
        boolean res = sections.containsKey(sectionName);
		sections.put(sectionName, sectionContent);
        return res;
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
	public String getSectionText(@NotNull String sectionName) {
        Preconditions.checkNotNull(sectionName, "SectionName should not be null");
		if (!sections.containsKey(sectionName)){
			throw new UnknownElementException(sectionName);
		}
		return sections.get(sectionName);
	}

}
