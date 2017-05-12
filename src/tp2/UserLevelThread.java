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
	
	public UserLevelThread(ArrayList<Task> taskArray, int ID) {
		this.taskArray = taskArray;
		this.index = 0;
		this.ID = ID;
		this.runningTask = taskArray.get(index);
		if (runningTask.getType() == Task.Type.IO) {
			state = ThreadState.BLOCKED;
		}
		else
			state = ThreadState.NONBLOCKED;
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
	
}
