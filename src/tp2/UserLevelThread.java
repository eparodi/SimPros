package tp2;
import java.util.*;

public class UserLevelThread {
	
	public static enum ThreadState {
		NONBLOCKED, BLOCKED, FINISHED
	};
	
	private ArrayList<Task> taskArray;
	private Task runningTask;
	private int index;
	private int ID;
	private ThreadState state;
	private int arrivalTime;
	private int needTime;
	
	public UserLevelThread(ArrayList<Task> taskArray, int ID, int arrivalTime) {
		this.taskArray = taskArray;
		this.index = 0;
		this.ID = ID;
		this.arrivalTime = arrivalTime;
		this.runningTask = taskArray.get(index);
		if (runningTask.getType() == Task.Type.IO) {
			throw new RuntimeException("A taskArray started with an IO task");
		}
		else
			state = ThreadState.NONBLOCKED;
		needTime = 0;
		for (Task task : taskArray) {
			if (task.getType() != Task.Type.IO)
				needTime += task.getAmount();
		}
	}
	
	public ThreadState getState() {
		return state;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getDevice() {
		if (runningTask.getType() == Task.Type.IO)
			return runningTask.getDevice();
		else
			return -1;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getNeedTime() {
		return needTime;
	}
	
	public void decrementArrivalTime() {
		if (arrivalTime == 0)
			throw new RuntimeException("Tried to decrement an arrival time of 0");
		else
			arrivalTime = arrivalTime - 1;
	}
	
	public void decrementNeedTime() {
		if (needTime == 0)
			throw new RuntimeException("Tried to decrement a need time of 0");
		else
			needTime = needTime - 1;
	}
	
	public void run() {
		runningTask.run();
		if (runningTask.isFinished()) {
			index ++;
			if (index < taskArray.size()) {
				runningTask = taskArray.get(index);
				if (runningTask.getType() == Task.Type.IO) {
					state = ThreadState.BLOCKED;
				}
				else
					state = ThreadState.NONBLOCKED;
			}
			else
				state = ThreadState.FINISHED;
		}
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !o.getClass().equals(getClass()))
			return false;
		UserLevelThread ult = (UserLevelThread) o;
		if (this.ID != (ult.getID()))
			return false;
		return true;
	}
	
	public int hashCode() {
		return this.ID;
	}
	
}
