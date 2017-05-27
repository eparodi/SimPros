package tp2;

import java.util.*;

public class RR implements Algorithm {

	private Queue<UserLevelThread> readyQueue;
	private ArrayList<UserLevelThread> newArray;
	private long quantum;
	private long currentQuantum;
	
	public RR(ArrayList<UserLevelThread> ultArray, long quantum) {
		readyQueue = new LinkedList<>();
		newArray = new ArrayList<>();
		for (UserLevelThread ult : ultArray) {
			if (ult.getArrivalTime() == 0)
				readyQueue.offer(ult);
			else
				newArray.add(ult);
		}
		this.quantum = quantum;
	}
	
	@Override
	public UserLevelThread getFirst() {
		this.currentQuantum = quantum;
		return readyQueue.poll();
	}

	@Override
	public UserLevelThread update(UserLevelThread ult) {
		switch(ult.getState()) {
			case NONBLOCKED: {
				currentQuantum = currentQuantum - 1;
				return ult;
			}
			case BLOCKED: {
				/*
				 * We want to conserve the blocked ult as the running one. Then if necessary we change it with pickNext.
				 */
				currentQuantum = currentQuantum - 1;
				return ult;
			}
			case FINISHED: {
				currentQuantum = quantum;
				return readyQueue.poll();
			}
		}
		return null;
	}
	
	@Override
	public UserLevelThread pickNext(UserLevelThread ult) {
		if (currentQuantum == 0) {
			currentQuantum = quantum;
			readyQueue.offer(ult);
			return readyQueue.poll();
		}
		return ult;
	}

	@Override
	public void updateNewUlts() {
		for (Iterator<UserLevelThread> iterator = newArray.iterator(); iterator.hasNext();) {
			UserLevelThread ult = iterator.next();
			ult.decrementArrivalTime();
			if (ult.getArrivalTime() == 0) {
				readyQueue.offer(ult);
				iterator.remove();
			}
		}
	}
	
}
