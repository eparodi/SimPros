package tp2;
import java.util.*;

public class UserLevelThread {
	
	public static enum ThreadState {
		NONBLOCKED, BLOCKED, FINISHED
	}
	
	private ArrayList<Task> task_array;
	private Task running_task;
	private int index;
	private ThreadState state;
	
	public UserLevelThread(ArrayList<Task> task_array) {
		this.task_array = task_array;
		this.index = 0;
		this.running_task = task_array.get(index);
		if (running_task.getType() == Task.Type.IO) {
			state = ThreadState.BLOCKED;
		}
		else
			state = ThreadState.NONBLOCKED;
	}
	
	public ThreadState getState() {
		return state;
	}
	
	public Task.Device getDevice() {
		if (running_task.getType() == Task.Type.IO)
			return running_task.getDevice();
		else
			return null;
	}
	
	public void run() {
		running_task.run();
		if (running_task.isFinished()) {
			index ++;
			if (index < task_array.size()) {
				running_task = task_array.get(index);
				if (running_task.getType() == Task.Type.IO) {
					state = ThreadState.BLOCKED;
				}
				else
					state = ThreadState.NONBLOCKED;
			}
			else
				state = ThreadState.FINISHED;
		}
	}
	
}
