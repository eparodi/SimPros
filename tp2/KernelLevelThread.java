package tp2;
import java.util.*;

import tp2.UserLevelThread.ThreadState;

public class KernelLevelThread {
	
	private ThreadLibrary thread_library;
	
	private ArrayList<Task> task_array;
	private UserLevelThread.ThreadState state;
	private Task running_task;
	private int index;
	
	public KernelLevelThread(ArrayList<UserLevelThread> user_thread_array, ThreadLibrary.Algorithm algorithm) {
		thread_library = new ThreadLibrary(user_thread_array, algorithm);
	}
	
	public KernelLevelThread(ArrayList<UserLevelThread> user_thread_array, ThreadLibrary.Algorithm algorithm, long quantum) {
		thread_library = new ThreadLibrary(user_thread_array, algorithm, quantum);
	}
	
	public KernelLevelThread(ArrayList<Task> task_array) {
		this.task_array = task_array;
		index = 0;
		running_task = task_array.get(index);
		if (running_task.getType() == Task.Type.IO)
			state = UserLevelThread.ThreadState.BLOCKED;
		else
			state = UserLevelThread.ThreadState.NONBLOCKED;
	}
	
	public UserLevelThread.ThreadState getState() {
		if (task_array == null)
			return thread_library.getState();
		else
			return state;
	}
	
	public Task.Device getDivice() {
		if (task_array == null)
			return thread_library.getDevice();
		else
			return running_task.getDevice();
	}
	
	public void run() {
		if (task_array == null) {
			thread_library.run();
			return;
		}
		running_task.run();
		if (running_task.isFinished()) {
			index ++;
			if (index < task_array.size()) {
				running_task = task_array.get(index);
				if (running_task.getType() == Task.Type.IO) {
					state = ThreadState.BLOCKED;
				}
				else
					state = ThreadState.FINISHED;
			}
		}
	}
	
}
