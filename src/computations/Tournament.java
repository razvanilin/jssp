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
public class Tournament {
	
	private int tournamentSize;
	private Problem problem;
	private int[][][] population;

	private int bestFitness;
	private ArrayList<Integer> winnerList;
	private ArrayList<Integer> loserList;
	
	/*
	 * Constructor 
	 */
	public Tournament(int tournamentSize, Problem problem, int[][][] population) {
		this.tournamentSize = tournamentSize;
		this.problem = problem;
		winnerList = new ArrayList<Integer>();
		loserList = new ArrayList<Integer>();
		this.population = population;
	}
	
	// ---------------------------------------------
	public int startTournament() {
		
		// create a tournament
		int[] potentialParents = new int[tournamentSize];
		
		Random rand = new Random();
		
		int winner = 0;
		int loser = 0;
			
		for (int i=0; i<tournamentSize; i++) {
			potentialParents[i] = rand.nextInt(population.length);
			System.out.println("Candidate with fitness: " + JSSP.getFitness(population[potentialParents[i]], problem));
		}
		
		// save the fitness of the first potential parent
		int bestFitness = JSSP.getFitness(population[potentialParents[0]], problem);
		
		int worstFitness = JSSP.getFitness(population[potentialParents[0]], problem);
		
		// choose the parent with the best fitness
		for (int i=0; i<tournamentSize; i++) {
			if (JSSP.getFitness(population[potentialParents[i]], problem) < bestFitness) {
				bestFitness = JSSP.getFitness(population[potentialParents[i]], problem);
				winner = potentialParents[i];
			}
			
			if (JSSP.getFitness(population[potentialParents[i]], problem) > worstFitness) {
				worstFitness = JSSP.getFitness(population[potentialParents[i]], problem);
				loser = potentialParents[i];
			}
		}
		
		winnerList.add(winner);
		loserList.add(loser);
		
		// return the winner
		return winner;
	}
	
	// ---------------------------------------------
	public ArrayList<Integer> getWinners() {
		return winnerList;
	}
	
	// ---------------------------------------------
	public ArrayList<Integer> getLosers() {
		return loserList;
	}
	
	// ---------------------------------------------
	public void refreshLists() {
		this.winnerList.clear();
		this.loserList.clear();
	}
}
