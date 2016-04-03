package set10107;

import java.util.Random;

import modelP.JSSP;
import modelP.Problem;

public class JsspExample {

	public static void main(String[] args) {

		/**
		 * Get a Problem instance using the static JSSP class in the range [1-142]
		 */
		Problem problem = JSSP.getProblem(86);		
	
		//print problem to std.out
		JSSP.printProblem(problem);
		
				
		/**
		 * Get a randomly initialised solution for the problem
		 */
//		int[][] solution2 = JSSP.getRandomSolution(problem);
//		int[][] solution = {{3,4,5,1,2,0},{0,2,4,3,5,1},{0,3,1,5,4,2},{0,1,4,3,2,5},{2,0,3,4,1,5},{4,0,5,1,3,2}};
		
		//print to std.out (also prints fitness)
//		JSSP.printSolution(solution, problem);
		
//		for (int i=0; i< solution2[0].length; i++) {
//			System.out.println(solution2[0][i]);
//		}
				
		
		/**
		 * Check the solution and return its fitness (invalid solutions return Integer.MaxValue)
		 */
//		int fitness = JSSP.getFitness(solution, problem);
//		System.out.println("Fitness = " + fitness);
		
		/**
		 * Save the solution (defaults to the project directory saving to a .txt file prefixed with the current computer time in milliseconds) 
		 * The filename is returned
		 */
//		String filename = JSSP.saveSolution(solution, problem);
		
		/**
		 * load solution from default directory
		 */
//		int[][] solution2 = JSSP.loadSolution(filename); 
	
		/**
		 * get the problem Id from the saved solution 
		 */
//		int id = JSSP.getProblemIdFromSolution(filename);
		/**
		 * load the associated problem
		 */
//		Problem problem2 = JSSP.getProblem(id);
		
		/**
		 * Check the solution and print to std.out 
		 */
//		JSSP.printSolution(solution2, problem2);
		
		
		/**
		 * Display a saved solution graphically 
		 */
//		JSSP.displaySolution(filename);	
		
		/**
		 * Create population 
		 */
		
		int populationSize = 20;
		int[][][] population = new int[populationSize][problem.getNumberOfMachines()][problem.getNumberOfJobs()];
		for(int i = 0; i < populationSize; i++){
			population[i] = JSSP.getRandomSolution(problem);
		}
		
		int generations = 5;
		for (int g=0; g<generations; g++) {
			// create a tournament
			int tournamentSize = 10;
			int[] potentialParents = new int[tournamentSize];
			
			Random rand = new Random();
			int[] crossoverCandidates = new int[2];
	
			for (int t=0; t<2; t++) {
				
				for (int i=0; i<tournamentSize; i++) {
					potentialParents[i] = rand.nextInt(populationSize);
				}
				
				// save the fitness of the first potential parent
				int bestFitness = JSSP.getFitness(population[potentialParents[0]], problem);
				
				int worstFitness = JSSP.getFitness(population[potentialParents[0]], problem);
				int worstCandidate = 0;
				
				// choose the parent with the best fitness
				for (int i=0; i<tournamentSize; i++) {
					if (JSSP.getFitness(population[potentialParents[i]], problem) < bestFitness) {
						bestFitness = JSSP.getFitness(population[potentialParents[i]], problem);
						crossoverCandidates[t] = potentialParents[i];
					}
					
					if (JSSP.getFitness(population[potentialParents[i]], problem) > worstFitness) {
						worstFitness = JSSP.getFitness(population[potentialParents[i]], problem);
						worstCandidate = potentialParents[i];
					}
				}
				
				// remove the worst candidate from the population
				//population = removeCandidate(population, worstCandidate);
			}
			
			int candidate1Fitness = JSSP.getFitness(population[crossoverCandidates[0]], problem);
			int candidate2Fitness = JSSP.getFitness(population[crossoverCandidates[1]], problem);
			
			int chosenParent = crossoverCandidates[0];
			int bestFitness = candidate1Fitness;
			
			
			if (bestFitness > candidate2Fitness) {
				bestFitness = candidate2Fitness;
				chosenParent = crossoverCandidates[1];
			}
			
			//System.out.println("Candidate 1 for the crossover is " + crossoverCandidates[0] + " with a fitness of: " + candidate1Fitness);
			//System.out.println("Candidate 2 for the crossover is " + crossoverCandidates[1] + " with a fitness of: " + candidate2Fitness);
	
			// crossover
			int crossovers = 720;
			int[][] chosen = new int[population[crossoverCandidates[0]].length][population[crossoverCandidates[0]][0].length];
			
			chosen = population[crossoverCandidates[0]].clone();
			for (int i=0; i<crossovers; i++) {
				int randMachine = rand.nextInt(population[crossoverCandidates[0]].length);
				
				int[] temp = population[crossoverCandidates[0]][randMachine];
				population[crossoverCandidates[0]][randMachine] = population[crossoverCandidates[1]][randMachine];
				population[crossoverCandidates[1]][randMachine] = temp;
				
				if (JSSP.getFitness(population[crossoverCandidates[0]], problem) < bestFitness) {
					bestFitness = JSSP.getFitness(population[crossoverCandidates[0]], problem);
					chosenParent = crossoverCandidates[0];
				}
				
				if (JSSP.getFitness(population[crossoverCandidates[1]], problem) < bestFitness) {
					bestFitness = JSSP.getFitness(population[crossoverCandidates[1]], problem);
					chosenParent = crossoverCandidates[1];
				}
			}
			
			chosen = population[chosenParent].clone();
		
			//System.out.println("After the crossover the candidate has a fitness of: " + JSSP.getFitness(chosen, problem));
			
			// Mutation
			int mutationTimes = 10;
			
			for (int i=0; i<mutationTimes; i++) {
				int machineReplacement1 = rand.nextInt(chosen.length);
				int machineReplacement2 = rand.nextInt(chosen.length);
	
				int[] temp = chosen[machineReplacement1];
				chosen[machineReplacement1] = chosen[machineReplacement2];
				chosen[machineReplacement2] = temp;
				
				for (int j=0; j<mutationTimes; j++) {
					int jobReplacement1 = rand.nextInt(chosen[0].length);
					int jobReplacement2 = rand.nextInt(chosen[0].length);
					
					int tempJob = chosen[machineReplacement1][jobReplacement1];
					chosen[machineReplacement1][jobReplacement1] = chosen[machineReplacement1][jobReplacement2]; 
					chosen[machineReplacement1][jobReplacement2] = tempJob;
					
					if (JSSP.getFitness(chosen, problem) < bestFitness) {
						bestFitness = JSSP.getFitness(chosen, problem);
						population[chosenParent] = chosen.clone();						
					}
				}
				
			}
			
			
			System.out.println("After " + mutationTimes + " mutations, the fitness is: " + JSSP.getFitness(population[chosenParent], problem));
			System.out.println("Generation " + g + " completed.");
		}
		
		int bestOverallFitness = JSSP.getFitness(population[0], problem);
		int bestOverallCandidate = 0;
		
		for (int i=0; i<population.length; i++) {
			if (JSSP.getFitness(population[i], problem) < bestOverallFitness) {
				bestOverallFitness = JSSP.getFitness(population[i], problem);
				bestOverallCandidate = i;
			}
		}
		
		System.out.println("The best overall candidate has a fitness of " + bestOverallFitness);
		
		String filename = JSSP.saveSolution(population[bestOverallCandidate], problem);
		JSSP.printSolution(population[bestOverallCandidate], problem);
		JSSP.displaySolution(filename);

	}
	
	
	public static int[][][] removeCandidate(int[][][] population, int candidate) {
		int [][][] newPopulation = new int[population.length][population[0].length][population[0][0].length];
		
		for(int i=0; i<population.length; i++) {
			if (i != candidate) {
				newPopulation[i] = population[i];
			}
		}
		
		return newPopulation;
		
	}
}
