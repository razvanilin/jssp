/**
 * 
 */
package entities;

import java.util.ArrayList;
import java.util.Random;

import modelP.JSSP;

/**
 * @author Razvan Ilin
 *
 */
public class Solution {
	ArrayList<Machine> machineList = new ArrayList<Machine>();
	
	public Solution(int[][] machines) {
		for (int i=0; i<machines.length; i++) {
			machineList.add(new Machine(machines[i]));
		}
	}
	
	@SuppressWarnings("unchecked")
	public Solution(ArrayList<Machine> machines) {
		machineList = (ArrayList<Machine>)machines.clone();
	}
	
	public ArrayList<Machine> getMachineList() {
		return this.machineList;
	}
	
	public void setSolution(ArrayList<Machine> machines) {
		this.machineList = machines;
	}
	
	public void permutateRandomly() {
		Random rand = new Random();
		int index1=0, index2=0;
		
		index1 = rand.nextInt(machineList.size()-1);
		do {
			index2 = rand.nextInt(machineList.size()-1);
		} while(index1 == index2);
		
		Machine temp = machineList.get(index1);
		machineList.set(index1, machineList.get(index2));
		machineList.set(index2, temp);
	}
	
	public void permutate(int index1, int index2) {
		Machine temp = machineList.get(index1);
		machineList.set(index1, machineList.get(index2));
		machineList.set(index2, temp);
	}
	
	public int[][] getFormattedSolution() {
		int[][] tempSolution = new int[machineList.size()][];
		Integer[] tempJobs;
		for (int i=0; i<machineList.size(); i++) {
			tempJobs = machineList.get(i).getJobs().toArray(new Integer[machineList.get(i).getJobs().size()]);
			tempSolution[i] = new int[tempJobs.length];
			
			for(int j=0; j<tempJobs.length; j++) {
				tempSolution[i][j] = tempJobs[j].intValue();
			}
		}
		
		return tempSolution;
	}
}
