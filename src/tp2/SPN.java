package tp2;

import java.util.*;

public class SPN implements Algorithm {
	
	private ArrayList<UserLevelThread> readyArray;
	private ArrayList<UserLevelThread> newArray;
	
	public SPN(ArrayList<UserLevelThread> ultArray) {
		readyArray = new ArrayList<>();
		newArray = new ArrayList<>();
		sort(ultArray);
		for (UserLevelThread ult : ultArray) {
			if (ult.getArrivalTime() == 0)
				readyArray.add(ult);
			else
				newArray.add(ult);
		}
	}

	@Override
	public UserLevelThread getFirst() {
		UserLevelThread ult = readyArray.get(0);
		readyArray.remove(0);
		return ult;
	}

	@Override
	public UserLevelThread update(UserLevelThread ult) {
		updateNewUltsSPN();
		if (ult.getState() == UserLevelThread.ThreadState.FINISHED) {
			if (readyArray.isEmpty())
				return null;
			UserLevelThread next = readyArray.get(0);
			readyArray.remove(0);
			return next;
		}
		return ult;
	}
	
	@Override
	public UserLevelThread pickNext(UserLevelThread ult) {
		return ult;
	}

	@Override
	public void updateNewUlts() {}
	
	private void updateNewUltsSPN() {
		for (Iterator<UserLevelThread> iterator = newArray.iterator(); iterator.hasNext();) {
			UserLevelThread ult = iterator.next();
			ult.decrementArrivalTime();
			if (ult.getArrivalTime() == 0) {
				insertInReady(ult);
				iterator.remove();
			}
		}
	}
	
	private void sort(ArrayList<UserLevelThread> array) {
		for (int i = 0; i < array.size(); i++) {
			int index = 0;
			UserLevelThread minUlt = array.get(index);
			for (int j = i; j < array.size(); j++) {
				UserLevelThread currentUlt = array.get(j);
				if (currentUlt.getNeedTime() < minUlt.getNeedTime()) {
					minUlt = currentUlt;
					index = j;
				}
			}
			array.set(index, array.get(i));
			array.set(i, minUlt);
		}
	}
	
	private void insertInReady(UserLevelThread ult) {
		boolean found = false;
		int index = 0;
		for (int i = 0; i < readyArray.size() && !found; i++) {
			if (readyArray.get(i).getNeedTime() > ult.getNeedTime()) {
				found = true;
				index = i;
			}
		}
		readyArray.add(index, ult);
	}
	
}
