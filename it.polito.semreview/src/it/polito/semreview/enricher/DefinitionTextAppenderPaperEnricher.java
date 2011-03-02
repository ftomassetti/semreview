package it.polito.semreview.enricher;

import it.polito.semreview.dataset.Paper;

import java.util.List;

public class DefinitionTextAppenderPaperEnricher extends AbstractSemanticPaperEnricher {

	@Override
	protected String combine(Paper paper, List<String> resourceDefinitions) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(paper.getTitle());
		buffer.append(paper.getAbstract());
		buffer.append(paper.getIntroduction());
		buffer.append(paper.getConclusions());
		for (String resourceDefinition : resourceDefinitions){
			buffer.append(resourceDefinition);
		}
		
		return buffer.toString();
	}

}
