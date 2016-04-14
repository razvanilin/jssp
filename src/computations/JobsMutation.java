/**
 * 
 */
package computations;

import entities.Population;
import entities.Solution;
import modelP.JSSP;
import modelP.Problem;

/**
 * @author Razvan Ilin
 *
 */
public class JobsMutation {
	
	private int mutations;
	private Problem problem;
	private Population population;
	private Solution bestMutation;
	
	public JobsMutation(Problem problem, int mutations) {
		this.problem = problem;
		this.mutations = mutations;
		population = Population.getInstance();
	}
	
	public void startMutation(int index) {
		Solution sol = new Solution(population.getSolution(index).getMachineList());
		
		// first permute the machines in the solution
		for (int i=0; i < mutations; i++) {
			// create a temporary solution and permute the machines inside until a better solution is found
			Solution tempSolution = new Solution(sol.getMachineList());
			tempSolution.permutateRandomly();
			
			if (JSSP.getFitness(tempSolution.getFormattedSolution(), problem) < JSSP.getFitness(sol.getFormattedSolution(), problem)) {
				sol = new Solution(tempSolution.getMachineList());
			}
		}
		
		// permute jobs inside the machines
		for (int i=0; i<sol.getMachineList().size(); i++) {
			// save the solution into a temporary variable until a better solution is found
			Solution tempSolution = new Solution(sol.getMachineList());
			
			for (int j=0; j<mutations; j++) {
				tempSolution.getMachineList().get(i).setJobs(tempSolution.getMachineList().get(i).getJobs());
				
				if (JSSP.getFitness(tempSolution.getFormattedSolution(), problem) < JSSP.getFitness(sol.getFormattedSolution(), problem)) {
					sol = new Solution(tempSolution.getMachineList());
				}
			}
		}
		
		bestMutation = new Solution(sol.getMachineList());
	}
	
	public Solution getMutation() {
		return bestMutation;
	}
}
