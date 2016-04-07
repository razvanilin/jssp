/**
 * 
 */
package computations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import modelP.JSSP;
import modelP.Problem;

/**
 * @author Razvan Ilin
 *
 */
public class Crossover {
	
	private int crossovers;
	private int[][][] population;
	private Problem problem;
	
	/*
	 * Constructor
	 */
	public Crossover(int crossovers, Problem problem, int[][][] population) {
		this.population = population;
		this.problem = problem;
		this.crossovers = crossovers;
	}
	
	// ---------------------------------------------
	public int[][] permutate(int[] crossoverCandidates) {
		
		// the candidates list must contain 2 values
		if (crossoverCandidates.length != 2) return null;
		
		Random rand =  new Random();
		int[][][] machines = new int[2][6][6];
		machines[0] = population[crossoverCandidates[0]];
		machines[1] = population[crossoverCandidates[1]];
		
		int bestFitness = JSSP.getFitness(machines[1], problem);
		int chosenParent = 1;
		
		if (JSSP.getFitness(population[crossoverCandidates[0]], problem) < JSSP.getFitness(machines[1], problem)) {
			bestFitness = JSSP.getFitness(machines[0], problem);
			chosenParent = 0;
		}
		
//		System.out.println("Before Crossover the fitness is: " + bestFitness);
		
		// crossover
		
		for (int i=0; i<crossovers; i++) {
			int randMachine = rand.nextInt(machines[0].length);
			
			int[] temp = machines[0][randMachine];
			machines[0][randMachine] = machines[1][randMachine];
			machines[1][randMachine] = temp;
			
			if (JSSP.getFitness(machines[0], problem) < bestFitness) {
				bestFitness = JSSP.getFitness(machines[0], problem);
				chosenParent = 0;
			}
			
			if (JSSP.getFitness(machines[1], problem) < bestFitness) {
				bestFitness = JSSP.getFitness(machines[1], problem);
				chosenParent = 1;
			}
		}
		
//		System.out.println("After the crossover the fitness is: " + JSSP.getFitness(machines[chosenParent], problem));
//		System.out.println();
		
		return machines[chosenParent];
	}
	
	public int[][] copyOf(int[][] original) {
	    int[][] copy = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original.length);
	    }
	    return copy;
	}
}
