/**
 * 
 */
package entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Razvan Ilin
 *
 */
public class Machine {
	private ArrayList<Integer> jobs = new ArrayList<Integer>();
	
	public Machine(int[] jobs) {
		for (int i=0; i<jobs.length; i++) {
			this.jobs.add(jobs[i]);
		}
	}
	
	public ArrayList<Integer> getJobs() {
		return this.jobs;
	}
	
	@SuppressWarnings("unchecked")
	public void setJobs(ArrayList<Integer> jobs) {
		this.jobs = (ArrayList<Integer>)jobs.clone();
	}
	
	public void permutateRandomly() {
		Random rand = new Random();
		int index1=0, index2=0;
		
		index1 = rand.nextInt(jobs.size()-1);
		do {
			index2 = rand.nextInt(jobs.size()-1);
		} while(index1 == index2);
		
		int temp = jobs.get(index1);
		jobs.set(index1, jobs.get(index2));
		jobs.set(index2, temp);
	}
	
	public void permutate(int index1, int index2) {
		int temp = jobs.get(index1);
		jobs.set(index1, jobs.get(index2));
		jobs.set(index2, temp);
	}
}
