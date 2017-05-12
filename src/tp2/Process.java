package tp2;
import java.util.*;

public class Process {
	
	private ArrayList<KernelLevelThread> kernelThreadsArray;
	private int ID;
	private int startTime;
	
	public Process(ArrayList<KernelLevelThread> kernelThreadsArray, int ID, int startTime) {
		this.kernelThreadsArray = kernelThreadsArray;
		this.ID = ID;
		this.startTime = startTime;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public ArrayList<KernelLevelThread> getKernelThreadsArray() {
		return kernelThreadsArray;
	}
	
	public int hashcode() {
		return ID;
	}
	
	public UserLevelThread.ThreadState getState() {
		boolean blocked = false;
		for (KernelLevelThread klt : kernelThreadsArray) {
			UserLevelThread.ThreadState state = klt.getState();
			switch(state) {
				case NONBLOCKED: {
					return state;
				}
				case BLOCKED: {
					blocked = true;
					break;
				}
				case FINISHED: {
					break;
				}
			}
		}
		if (blocked) {
			return UserLevelThread.ThreadState.BLOCKED;
		}
		return UserLevelThread.ThreadState.FINISHED;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !o.getClass().equals(getClass()))
			return false;
		Process process = (Process) o;
		if (this.ID != (process.getID()))
			return false;
		return true;
	}
	
}
