package it.polito.semreview.experiment;

public class AlgorithmResult {

	private int nIterations;
	private int nPapersExamined;
	private int iSize;

	public AlgorithmResult(int nIterations, int nPapersExamined, int iSize) {
		super();
		this.nIterations = nIterations;
		this.nPapersExamined = nPapersExamined;
		this.iSize = iSize;
	}

	@Override
	public String toString() {
		return "AlgorithmResult [nIterations=" + nIterations
				+ ", nPapersExamined=" + nPapersExamined + ", iSize=" + iSize
				+ "]";
	}

	public int getnIterations() {
		return nIterations;
	}

	public int getnPapersExamined() {
		return nPapersExamined;
	}

	public int getiSize() {
		return iSize;
	}

}
