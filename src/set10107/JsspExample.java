package set10107;

import java.util.Arrays;
import java.util.Random;

import computations.Crossover;
import computations.Mutation;
import computations.Tournament;
import modelP.JSSP;
import modelP.Problem;

public class JsspExample {

	public static void main(String[] args) {

		/**
		 * Get a Problem instance using the static JSSP class in the range [1-142]
		 */
		
		int problemNumber = Integer.parseInt(args[0]);
		int generations = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		int tournamentSize = Integer.parseInt(args[3]);
		int crossovers = Integer.parseInt(args[4]);
		int mutations = Integer.parseInt(args[5]);
		
		Problem problem = JSSP.getProblem(problemNumber);		
	
		//print problem to std.out
		//JSSP.printProblem(problem);
				
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
		int[][][] population = new int[populationSize][problem.getNumberOfMachines()][problem.getNumberOfJobs()];
		for(int i = 0; i < populationSize; i++){
			population[i] = JSSP.getRandomSolution(problem);
		}
		
		for (int gen = 0; gen<generations; gen++) {
			
			Tournament tournament = new Tournament(tournamentSize, problem, population);
			
			// run the tournament multiple times
			for (int i=0; i<2; i++) {
				tournament.startTournament();
			}
			
			// Do the crossover (permutation)
			Crossover crossover = new Crossover(crossovers, problem, population);
			
			int[] crossoverCandidates = new int[2];
			crossoverCandidates[0] = tournament.getWinners().get(0);
			crossoverCandidates[1] = tournament.getWinners().get(1);
			
			int[][] chosen = crossover.permutate(crossoverCandidates);
			
			
			// mutate the crossover candidate
			Mutation mutation = new Mutation(mutations, problem);
			int[][] mutatedCandidate = mutation.mutate(chosen);
			
			// replace the mutated candidate in the population
			Random rand = new Random();
			int replacement = tournament.getLosers().get(rand.nextInt(tournament.getLosers().size()));
			
			if (mutatedCandidate != null)
				population[replacement] = copyOf(mutatedCandidate);
			
			
			// get the best overall candidate
			int bestOverallFitness = JSSP.getFitness(population[0], problem);
			int bestOverallCandidate = 0;
			
			for (int i=0; i<population.length; i++) {
				if (JSSP.getFitness(population[i], problem) < bestOverallFitness) {
					bestOverallFitness = JSSP.getFitness(population[i], problem);
					bestOverallCandidate = i;
				}
			}
			
			System.out.println("JSSP: generation " + gen + "-fitness " + bestOverallFitness + "-solution "+jsonify(population[bestOverallCandidate]));
//			JSSP.printSolution(population[bestOverallCandidate], problem);
			
			//String filename = JSSP.saveSolution(population[bestOverallCandidate], problem);
			//JSSP.printSolution(population[bestOverallCandidate], problem);
			//JSSP.displaySolution(filename);
		}
		
	}
	
	public static int[][] copyOf(int[][] original) {
	    int[][] copy = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original.length);
	    }
	    return copy;
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
	
	public static String jsonify(int[][] solution) {
		String json = "[";
		for (int i = 0; i<solution.length; i++) {
			json+="[";
			for (int j=0; j<solution[i].length; j++) {
				json+= "" + solution[i][j] + ",";
				
				if (j == solution[i].length-1) {
					json = json.substring(0, json.length()-1);
				}
			}
			json+="],";
			
			if (i == solution.length-1) {
				json = json.substring(0, json.length()-1);
			}
		}
		
		json += "]";
		return json;
	}
}
