package pers.ParallelPrimeGenerator;

public class Range {

	public final int lowerBound;
	public final int upperBound;
	
	public Range(int lowerBound, int upperBound)
	{
		if (upperBound < lowerBound)
		{
			throw new IllegalArgumentException("upperBound should not be less than lowerBound!");
		}
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public String toString()
	{
		return "Range [" + lowerBound + ", " + upperBound + "]";
	}
	
}
