package ProcessManager;
import java.util.*;

public class KernelLevelThread {
	
	private ThreadLibrary thread_library;
	private ArrayList<Task> taskList;
	private Enum.ThreadState state;
	private Task runningTask;
	private int index; 
	
	public KernelLevelThread(ArrayList<UserLevelThread> user_thread_array, Enum.Algorithm algorithm) {
		thread_library = new ThreadLibrary(user_thread_array, algorithm);
	}
	
	public KernelLevelThread(ArrayList<UserLevelThread> user_thread_array, Enum.Algorithm algorithm, long quantum) {
		thread_library = new ThreadLibrary(user_thread_array, algorithm, quantum);
	}

	public KernelLevelThread(ArrayList<Task> task_array) {
		this.taskList = task_array;
		index = 0;
		runningTask = task_array.get(index);
		if (runningTask.getType() == Enum.Type.IO)
			state = Enum.ThreadState.BLOCKED;
		else
			state = Enum.ThreadState.NONBLOCKED;
	}
	
	public Enum.ThreadState getState() {
		if (taskList == null)
			return thread_library.getState();
		else
			return state;
	}
	
	public Enum.Device getDevice() {
		if (taskList == null)
			return thread_library.getDevice();
		else
			return runningTask.getDevice();
	}
	
	public void run() {
		if (taskList == null) {
			thread_library.run();
			return;
		}
		runningTask.run();
		if (runningTask.isFinished()) {
			index ++;
			if (index < taskList.size()) {
				runningTask = taskList.get(index);
				if (runningTask.getType() == Enum.Type.IO) {
					state = Enum.ThreadState.BLOCKED;
				}
				else
					state = Enum.ThreadState.FINISHED;
			}
		}
	}
	
}
