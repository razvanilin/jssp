/**
 * 
 */
package computations;

import java.util.Arrays;
import java.util.Random;

import modelP.JSSP;
import modelP.Problem;

/**
 * @author Razvan Ilin
 *
 */
public class Mutation {
	
	private int mutationTimes;
	private Problem problem;
	private int[][][] population;
	private int[][] chosen;
	
	/*
	 * Constructor
	 */
	public Mutation(int mutationTimes, Problem problem, int[][][] population) {
		this.mutationTimes = mutationTimes;
		this.problem = problem;
		this.population = population;
	}
	
	// ---------------------------------------------
	public int[][] mutate(int[][] candidate) {
		
		Random rand = new Random();
		int[][] mutationCandidate = candidate.clone();
		//int[][] chosen = null;
		
		int bestFitness = JSSP.getFitness(mutationCandidate, problem);
		
		
		for (int i=0; i<mutationTimes; i++) {
			int machineReplacement1 = rand.nextInt(mutationCandidate.length);
			int machineReplacement2 = rand.nextInt(mutationCandidate.length);

			int[] temp = mutationCandidate[machineReplacement1];
			mutationCandidate[machineReplacement1] = mutationCandidate[machineReplacement2];
			mutationCandidate[machineReplacement2] = temp;
			
			for (int j=0; j<mutationTimes; j++) {
				int jobReplacement1 = rand.nextInt(mutationCandidate[0].length);
				int jobReplacement2 = rand.nextInt(mutationCandidate[0].length);
				
				int tempJob = mutationCandidate[machineReplacement1][jobReplacement1];
				mutationCandidate[machineReplacement1][jobReplacement1] = mutationCandidate[machineReplacement1][jobReplacement2]; 
				mutationCandidate[machineReplacement1][jobReplacement2] = tempJob;
				
				if (JSSP.getFitness(mutationCandidate, problem) < bestFitness) {
					bestFitness = JSSP.getFitness(mutationCandidate, problem);
					chosen = copyOf(mutationCandidate);						
				}
			}
			
		}
				
		return chosen;
	}
	
	private int[][] copyOf(int[][] original) {
	    int[][] copy = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original.length);
	    }
	    return copy;
	}
}
