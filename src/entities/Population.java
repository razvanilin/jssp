package entities;

import java.util.ArrayList;

public class Population {
	
	private static Population population = new Population();
	private ArrayList<Solution> solutionList;
	
	private Population() {
		solutionList = new ArrayList<Solution>();
	}
	
	public static Population getInstance() {
		return population;
	}
	
	public ArrayList<Solution> getSolutions() {
		return solutionList;
	}
	
	public Solution getSolution(int index) {
		return solutionList.get(index);
	}
	
	public void addSolution(Solution solution) {
		solutionList.add(solution);
	}
	
	public void removeSolution(int index) {
		solutionList.remove(index);
	}
	
	public void mixPopulation(int replacement, int replacer) {
		solutionList.set(replacement, solutionList.get(replacer));
	}
	
	public void setSolution(int index, Solution solution) {
		solutionList.set(index, solution);
	}
	
	
}
