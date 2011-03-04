package it.polito.semreview.enrichment.keyphrasesextraction;

public class KeyPhraseImpl implements KeyPhrase {

	private String text;
	
	public KeyPhraseImpl(String text) {
		super();
		this.text = text;
	}

	@Override
	public String toString() {
		return "KeyPhraseImpl [text=" + text + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		KeyPhraseImpl other = (KeyPhraseImpl) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String text() {
		return text;
	}

}
