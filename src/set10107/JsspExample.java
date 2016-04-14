package set10107;

import java.util.Arrays;
import java.util.Random;

import computations.JobsMutation;
import computations.MachineCrossover;
import computations.SolutionTournament;
import entities.Population;
import entities.Solution;
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
		Population pop = Population.getInstance();
	

		int[][][] population = new int[populationSize][problem.getNumberOfMachines()][problem.getNumberOfJobs()];
		for(int i = 0; i < populationSize; i++){
			int[][] tempSol = JSSP.getRandomSolution(problem);
			population[i] = tempSol;
			pop.addSolution(new Solution(tempSol));
		}
	
		SolutionTournament solTournament = new SolutionTournament(problem, tournamentSize);
		
		// GENERATIONS LOOP
		for (int i=0; i<generations; i++) {
				
			for (int j = 0; j < 2; j++) {
				solTournament.startTournament();
			}
			
			// crossover two random winners
			Random rand = new Random();
			int maxRand = solTournament.getWinnerList().size();
			
			MachineCrossover machineCrossover = new MachineCrossover(problem, crossovers);
			machineCrossover.startCrossover(
					solTournament.getWinnerList().get(rand.nextInt(maxRand)), 
					solTournament.getWinnerList().get(rand.nextInt(maxRand))
			);
			
			
			JobsMutation jobsMutation = new JobsMutation(problem, mutations);
			jobsMutation.startMutation(machineCrossover.getBestCandidateIndex());

			
			pop.setSolution(machineCrossover.getBestCandidateIndex(), new Solution(jobsMutation.getMutation().getMachineList()));

			
			// get the best overall candidate
			int bestOverallFitness = JSSP.getFitness(pop.getSolution(0).getFormattedSolution(), problem);
			int bestOverallCandidate = 0;
			
			for (int k=0; k<pop.getSolutions().size(); k++) {
				if (JSSP.getFitness(pop.getSolution(k).getFormattedSolution(), problem) < bestOverallFitness) {
					bestOverallFitness = JSSP.getFitness(pop.getSolution(k).getFormattedSolution(), problem);
					bestOverallCandidate = k;
				}
			}
			
			System.out.println("JSSP: generation " + i + "-fitness " + bestOverallFitness + "-solution "+jsonify(pop.getSolution(bestOverallCandidate).getFormattedSolution()));
		}
		
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
