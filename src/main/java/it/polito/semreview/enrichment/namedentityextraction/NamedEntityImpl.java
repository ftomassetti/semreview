package it.polito.semreview.enrichment.namedentityextraction;

import java.net.URI;

public class NamedEntityImpl implements NamedEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String type;
	private URI uri;
	private Double similarityScore;
	private Integer support;
	
	public NamedEntityImpl( String name, String type, String uri, 
						    String similarityScore, String support ) 
	{
		this.name = name;
		this.type = type;
		this.uri = URI.create(uri);
		this.similarityScore = Double.parseDouble( similarityScore );
		this.support = Integer.parseInt( support );
	}
	
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String type() {
		return this.type;
	}

	@Override
	public URI uri() {
		return this.uri;
	}

	@Override
	public Double similarityScore() {
		return this.similarityScore;
	}

	@Override
	public Integer support() {
		return this.support;
	}
	
	@Override
	public String toString() {
		return "NameEntity [name=" + name + ",type=" + type + 
				",uri=" + uri.toString() + "]";
	}

}
