package pers.ParallelPrimeGenerator;

import java.math.BigInteger;
import java.util.Set;

public class ParallelPrimeGeneratorEC {

	public static void main(String[] args) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		
		PrimeGeneratorService primeGeneratorService = new PrimeGeneratorService();
		
		Set<BigInteger> result = primeGeneratorService.generatePrime(Integer.valueOf("7483647"));
		
		System.out.println("Generated " + result.size() + " prime:");
		// System.out.println(result);
	}

}
