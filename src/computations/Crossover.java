/**
 * 
 */
package computations;

import java.util.ArrayList;
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
		machines[0] = population[crossoverCandidates[0]].clone();
		machines[1] = population[crossoverCandidates[1]].clone();
		
		int bestFitness = JSSP.getFitness(machines[1], problem);
		System.out.println("Best fitness before crossover: " + bestFitness);
		int chosenParent = crossoverCandidates[1];
		
		if (JSSP.getFitness(population[crossoverCandidates[0]], problem) < JSSP.getFitness(machines[1], problem)) {
			bestFitness = JSSP.getFitness(machines[0], problem);
			chosenParent = crossoverCandidates[0];
		}
		
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
		
		System.out.println("Best fitness after crossover: " + JSSP.getFitness(machines[chosenParent], problem));
		
		return machines[chosenParent];
	}
}
