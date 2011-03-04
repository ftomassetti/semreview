package it.polito.semreview.dataset;

public class PaperImpl implements Paper {
	
	private PaperId paperId;
	private String title;
	private String abstract_;
	private String introduction;
	private String conclusions;
	
	public String getTitle() {
		return title;
	}
	public String getAbstract() {
		return abstract_;
	}
	public String getIntroduction() {
		return introduction;
	}
	public String getConclusions() {
		return conclusions;
	}
	public PaperImpl(PaperId paperId, String title, String abstract_, String introduction,
			String conclusions) {
		super();
		this.paperId = paperId;
		this.title = title;
		this.abstract_ = abstract_;
		this.introduction = introduction;
		this.conclusions = conclusions;
	}
	@Override
	public PaperId getId() {
		return paperId;
	}
	@Override
	public String collateText() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(title);
		buffer.append(abstract_);
		buffer.append(introduction);
		buffer.append(conclusions);
		
		return buffer.toString();
	}


}
