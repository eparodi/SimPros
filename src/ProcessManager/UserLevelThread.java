package ProcessManager;
import java.util.*;

public class UserLevelThread {

	private ArrayList<Task> taskList;
	private Task runningTask;
	private int index;
	private Enum.ThreadState state;
	
	public UserLevelThread(ArrayList<Task> taskList) {
		this.taskList = taskList;
		this.index = 0;
		this.runningTask = taskList.get(index);
		if (runningTask.getType() == Enum.Type.IO) {
			state = Enum.ThreadState.BLOCKED;
		}
		else
			state = Enum.ThreadState.NONBLOCKED;
	}
	
	public Enum.ThreadState getState() {
		return state;
	}
	
	public Enum.Device getDevice() {
		if (runningTask.getType() == Enum.Type.IO)
			return runningTask.getDevice();
		else
			return null;
	}
	
	public void run() {
		runningTask.run();
		if (runningTask.isFinished()) {
			index ++;
			if (index < taskList.size()) {
				runningTask = taskList.get(index);
				if (runningTask.getType() == Enum.Type.IO) {
					state = Enum.ThreadState.BLOCKED;
				}
				else
					state = Enum.ThreadState.NONBLOCKED;
			}
			else
				state = Enum.ThreadState.FINISHED;
		}
	}
	
}
