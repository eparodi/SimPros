package tp2;

import java.util.*;

public class FIFO implements Algorithm {
	
	private Queue<UserLevelThread> readyQueue;
	private ArrayList<UserLevelThread> newArray;

	public FIFO(ArrayList<UserLevelThread> ultArray) {
		readyQueue = new LinkedList<>();
		newArray = new ArrayList<>();
		for (UserLevelThread ult : ultArray) {
			if (ult.getArrivalTime() == 0)
				readyQueue.offer(ult);
			else
				newArray.add(ult);
		}
	}
	
	@Override
	public UserLevelThread getFirst() {
		return readyQueue.poll();
	}

	@Override
	public UserLevelThread update(UserLevelThread ult) {
		if (ult.getState() == UserLevelThread.ThreadState.FINISHED)
			return readyQueue.poll();
		return ult;
	}
	
	@Override
	public UserLevelThread pickNext(UserLevelThread ult) {
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
