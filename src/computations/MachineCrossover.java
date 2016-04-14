/**
 * 
 */
package computations;

import java.util.ArrayList;
import java.util.Random;

import entities.Machine;
import entities.Population;
import entities.Solution;
import modelP.JSSP;
import modelP.Problem;

/**
 * @author Razvan Ilin
 *
 */
public class MachineCrossover {
	
	private int bestCandidateIndex;
	private Problem problem;
	private Population population;
	private int crossovers;
	
	public MachineCrossover(Problem problem, int crossovers) {
		this.problem = problem;
		population = Population.getInstance();
		this.crossovers = crossovers;
	}
	
	public Solution startCrossover(int index1, int index2) {
		Solution sol1 = new Solution(population.getSolution(index1).getMachineList());
		Solution sol2 = new Solution(population.getSolution(index2).getMachineList());
		
		int bestFitness = JSSP.getFitness(sol1.getFormattedSolution(), problem);
		bestCandidateIndex = index1;
		if (JSSP.getFitness(sol2.getFormattedSolution(), problem) < bestFitness) {
			bestFitness = JSSP.getFitness(sol2.getFormattedSolution(), problem);
			bestCandidateIndex = index2;
		}
		
		ArrayList<Machine> machineList1 = sol1.getMachineList();
		ArrayList<Machine> machineList2 = sol2.getMachineList();
		
		if (machineList1.size() != machineList2.size()) return null;
		
		Random rand = new Random();
		
		for (int i=0; i<crossovers; i++) {
			//System.out.println(JSSP.getFitness(population.getSolution(index1).getFormattedSolution(), problem));
			int randIndex = rand.nextInt(machineList1.size());
			
			Machine tempMachine = machineList1.get(randIndex);
			machineList1.set(randIndex, machineList2.get(randIndex));
			machineList2.set(randIndex, tempMachine);
			
			// create the temporary solutions
			
			// also check if the new solution is the best one yet
			if (JSSP.getFitness(sol1.getFormattedSolution(), problem) < bestFitness) {
				bestFitness = JSSP.getFitness(sol1.getFormattedSolution(), problem);
				bestCandidateIndex = index1;
				
				// replace the candidates in the population if the fitness is better
				if (JSSP.getFitness(population.getSolution(index1).getFormattedSolution(), problem)
						> JSSP.getFitness(sol1.getFormattedSolution(), problem)) {
					population.setSolution(index1, new Solution(sol1.getMachineList()));
				}
			}
			
			// also check if the new solution is the best one yet
			if (JSSP.getFitness(sol2.getFormattedSolution(), problem) < bestFitness) {
				bestFitness = JSSP.getFitness(sol2.getFormattedSolution(), problem);
				bestCandidateIndex = index2;
				
				if (JSSP.getFitness(population.getSolution(index2).getFormattedSolution(), problem)
						> JSSP.getFitness(sol2.getFormattedSolution(), problem)) {
					population.setSolution(index2, new Solution(sol2.getMachineList()));
					
				}
			}
		}
		
		return null;
	}
	
	public int getBestCandidateIndex() {
		return bestCandidateIndex;
	}
}
