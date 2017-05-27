package tp2;

import java.util.*;

public class HRRN implements Algorithm {
	
	ArrayList<UserLevelThread> readyArray;
	ArrayList<UserLevelThread> newArray;
	HashMap<UserLevelThread, Integer> waitingMap;

	public HRRN(ArrayList<UserLevelThread> ultArray) {
		readyArray = new ArrayList<>();
		newArray = new ArrayList<>();
		waitingMap = new HashMap<>();
		for (UserLevelThread ult : ultArray) {
			waitingMap.put(ult, 0);
			if (ult.getArrivalTime() == 0) {
				readyArray.add(ult);
			}
			else
				newArray.add(ult);
		}
	}
	
	@Override
	public UserLevelThread getFirst() {
		UserLevelThread highest = readyArray.get(0);
		float responseRatio = waitingMap.get(highest) / (float)highest.getNeedTime() + 1;
		for (int i = 0; i < readyArray.size(); i++) {
			UserLevelThread current = readyArray.get(i);
			float currentResponseRatio = (waitingMap.get(current) / (float)current.getNeedTime()) + 1;
			if (currentResponseRatio > responseRatio) {
				responseRatio = currentResponseRatio;
				highest = current;
			}
		}
		readyArray.remove(highest);
		return highest;
	}

	@Override
	public UserLevelThread update(UserLevelThread ult) {
		updateNewUltsHRRN();
		if (ult.getState() == UserLevelThread.ThreadState.FINISHED) {
			if (readyArray.size() == 0)
				return null;
			UserLevelThread highest = readyArray.get(0);
			float responseRatio = waitingMap.get(highest) / (float)highest.getNeedTime() + 1;
			for (int i = 0; i < readyArray.size(); i++) {
				UserLevelThread current = readyArray.get(i);
				float currentResponseRatio = (waitingMap.get(current) / (float)current.getNeedTime()) + 1;
				if (currentResponseRatio > responseRatio) {
					responseRatio = currentResponseRatio;
					highest = current;
				}
			}
			readyArray.remove(highest);
			for (UserLevelThread readyUlt : readyArray) {
				waitingMap.put(readyUlt, waitingMap.get(readyUlt) + 1);
			}
			return highest;
		}
		for (UserLevelThread readyUlt : readyArray) {
			waitingMap.put(readyUlt, waitingMap.get(readyUlt) + 1);
		}
		return ult;
	}

	@Override
	public UserLevelThread pickNext(UserLevelThread ult) {
		return ult;
	}
	
	private void updateNewUltsHRRN() {
		for (Iterator<UserLevelThread> iterator = newArray.iterator(); iterator.hasNext();) {
			UserLevelThread ult = iterator.next();
			ult.decrementArrivalTime();
			if (ult.getArrivalTime() == 0) {
				readyArray.add(ult);
				iterator.remove();
				waitingMap.put(ult, 0);
			}
		}
	}

	@Override
	public void updateNewUlts() {}

}
