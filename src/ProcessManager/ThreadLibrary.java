package ProcessManager;
import java.util.*;

public class ThreadLibrary {

	public static enum Algorithm {
		FIFO, RR, HRRN, SPN, SRT
	}
	
	private long quantum;
	private Algorithm algorithm;
	
	private Queue<UserLevelThread> ready_queue;
	private UserLevelThread running_ult;			// Whether it's blocked or not.
	
	public ThreadLibrary(ArrayList<UserLevelThread> user_thread_array, Algorithm algorithm) {
		ready_queue = new LinkedList<>();
		this.algorithm = algorithm;
		
		for (UserLevelThread ult : user_thread_array) {
			ready_queue.offer(ult);
		}
		running_ult = ready_queue.poll();
	}
	
	public ThreadLibrary(ArrayList<UserLevelThread> user_thread_array, Algorithm algorithm, long quantum) {
		this(user_thread_array, algorithm);
		this.quantum = quantum;
	}
	
	public UserLevelThread.ThreadState getState() {
		if (running_ult == null)
			return UserLevelThread.ThreadState.FINISHED;
		return running_ult.getState();
	}
	
	public Task.Device getDevice() {
		return running_ult.getDevice();
	}
	
	public void run() {
		if (running_ult.getState() == UserLevelThread.ThreadState.BLOCKED) {
			running_ult.run();
			if (running_ult.getState() == UserLevelThread.ThreadState.FINISHED)
				running_ult = ready_queue.poll();
			return;
		}
		switch(algorithm) {
			case FIFO: {
				running_ult.run();
				if (running_ult.getState() == UserLevelThread.ThreadState.FINISHED)
					running_ult = ready_queue.poll();
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