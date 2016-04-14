/**
 * 
 */
package computations;

import java.util.ArrayList;
import java.util.Random;

import entities.Population;
import entities.Solution;
import modelP.JSSP;
import modelP.Problem;

/**
 * @author Razvan Ilin
 *
 */
public class SolutionTournament {
	
	Problem problem;
	int tournamentSize;
	ArrayList<Integer> winnerList;
	ArrayList<Integer> loserList;
	Population population;
	
	// construct the tournament
	public SolutionTournament(Problem problem, int tournamentSize) {
		this.problem = problem;
		this.tournamentSize = tournamentSize;
		winnerList = new ArrayList<Integer>();
		loserList = new ArrayList<Integer>();
		population = Population.getInstance();
	}
	
	public void startTournament() {
		Random rand = new Random();
		ArrayList<Solution> chosenSolutions = new ArrayList<Solution>();
		
		int bestFitness = Integer.MAX_VALUE;
		int worstFitness = 0;
		int bestIndex = 0;
		int worstIndex = 0;
		
		// get candidates for the tournament and search for the best and worst candidate at the same time
		for (int i=0; i < tournamentSize; i++) {
			int randIndex = rand.nextInt(population.getSolutions().size());
			chosenSolutions.add(population.getSolution(randIndex));
			
			int[][] formattedSolution = chosenSolutions.get(chosenSolutions.size() - 1).getFormattedSolution();
			if (JSSP.getFitness(formattedSolution, problem) < bestFitness) {
				bestFitness = JSSP.getFitness(formattedSolution, problem);
				bestIndex = randIndex;
			}
			
			if (JSSP.getFitness(formattedSolution, problem) > worstFitness) {
				worstFitness = JSSP.getFitness(formattedSolution, problem);
				worstIndex = randIndex;
			}
		}
		
		// add the winner and loser index to the lists
		winnerList.add(bestIndex);
		loserList.add(worstIndex);
	}
	
	// in case the lists need to be emptied, to introduce a bigger random factor
	public void refreshLists() {
		winnerList.clear();
		loserList.clear();
	}
	
	// getters for the lists
	public ArrayList<Integer> getWinnerList() {
		return winnerList;
	}
	
	public ArrayList<Integer> getLoserList() {
		return loserList;
	}
	
}
