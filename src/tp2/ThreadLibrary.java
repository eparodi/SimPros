package tp2;
import java.util.*;

public class ThreadLibrary {

	public static enum Algorithm {
		FIFO, RR, HRRN, SPN, SRT
	};
	
	private long quantum;
	private Algorithm algorithm;
	
	private Queue<UserLevelThread> readyQueue;
	private UserLevelThread runningUlt;			// Whether it's blocked or not.
	
	public ThreadLibrary(ArrayList<UserLevelThread> user_thread_array, Algorithm algorithm) {
		readyQueue = new LinkedList<>();
		this.algorithm = algorithm;
		
		for (UserLevelThread ult : user_thread_array) {
			readyQueue.offer(ult);
		}
		runningUlt = readyQueue.poll();
	}
	
	public ThreadLibrary(ArrayList<UserLevelThread> user_thread_array, Algorithm algorithm, long quantum) {
		this(user_thread_array, algorithm);
		this.quantum = quantum;
	}
	
	public UserLevelThread.ThreadState getState() {
		if (runningUlt == null)
			return UserLevelThread.ThreadState.FINISHED;
		return runningUlt.getState();
	}
	
	public Integer getDevice() {
		return runningUlt.getDevice();
	}
	
	public UserLevelThread getRunningUlt() {
		return runningUlt;
	}
	
	public void run() {
		if (runningUlt.getState() == UserLevelThread.ThreadState.BLOCKED) {
			runningUlt.run();
			if (runningUlt.getState() == UserLevelThread.ThreadState.FINISHED)
				runningUlt = readyQueue.poll();
			return;
		}
		switch(algorithm) {
			case FIFO: {
				runningUlt.run();
				if (runningUlt.getState() == UserLevelThread.ThreadState.FINISHED)
					runningUlt = readyQueue.poll();
				break;
			}
			case RR: {
				break;
			}
			case HRRN: {
				break;
			}
			case SPN: {
				break;
			}
			case SRT: {
				break;
			}
		}
	}
	
}