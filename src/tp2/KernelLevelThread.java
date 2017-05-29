package tp2;
import java.util.*;

import tp2.UserLevelThread.ThreadState;

public class KernelLevelThread {
	
	private ThreadLibrary threadLibrary;
	
	private ArrayList<Task> taskArray;
	private UserLevelThread.ThreadState state;
	private Task runningTask;
	private int index;
	private int ID;
	private int processID;
	private int needTime;
	
	public KernelLevelThread(ArrayList<UserLevelThread> ultArray, int algorithm, int ID, int processID) {
		this(ultArray, algorithm, 0, ID, processID);
	}
	
	public KernelLevelThread(ArrayList<UserLevelThread> ultArray, int algorithm, long quantum, int ID, int processID) {
		threadLibrary = new ThreadLibrary(ultArray, algorithm, quantum);
		this.ID = ID;
		this.processID = processID;
		needTime = 0;
		for (UserLevelThread ult : ultArray) {
			needTime += ult.getNeedTime();
		}
	}
	
	public KernelLevelThread(ArrayList<Task> taskArray, int ID, int processID) {
		this.taskArray = taskArray;
		this.ID = ID;
		this.processID = processID;
		index = 0;
		runningTask = taskArray.get(index);
		if (runningTask.getType() == Task.Type.IO)
			state = UserLevelThread.ThreadState.BLOCKED;
		else
			state = UserLevelThread.ThreadState.NONBLOCKED;
		needTime = 0;
		for (Task task : taskArray) {
			if (task.getType() != Task.Type.IO)
				needTime += task.getAmount();
		}
	}
	
	public UserLevelThread.ThreadState getState() {
		if (taskArray == null)
			return threadLibrary.getState();
		else
			return state;
	}
	
	public int getDivice() {
		if (taskArray == null)
			return threadLibrary.getDevice();
		else
			return runningTask.getDevice();
	}
	
	public UserLevelThread getRunningUlt() {
		if (threadLibrary != null)
			return threadLibrary.getRunningUlt();
		return null;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getNeedTime() {
		return needTime;
	}
	
	public int getProcessID() {
		return processID;
	}

	public ThreadLibrary getThreadLibrary() {
		return this.threadLibrary;
	}
	
	public void run(TraceElement element) {
		if (taskArray == null) {
			threadLibrary.run(element);
			return;
		}
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
		KernelLevelThread klt = (KernelLevelThread) o;
		if (this.ID != (klt.getID()))
			return false;
		return true;
	}
	
	public int hashCode() {
		return ID;
	}
	
}
