package ProcessManager;
import java.util.*;

public class Process {

	private int processID;
	private Queue<KernelLevelThread> readyQueue;
	private ArrayList<Queue<KernelLevelThread>> blockedQueues;
	
	public Process(ArrayList<KernelLevelThread> klt_array, int devices_count) {
		readyQueue = new LinkedList<>();
		blockedQueues = new ArrayList<>();
		for (int i = 0; i < devices_count; i++) {
			blockedQueues.add(new LinkedList<>());
		}
		
		for (KernelLevelThread klt : klt_array) {
			if (klt.getState() == Enum.ThreadState.NONBLOCKED)
				readyQueue.offer(klt);
			else {
				Enum.Device device = klt.getDevice();
				switch(device) {
					case IO1: {
						blockedQueues.get(0).add(klt);
					}
					case IO2: {
						blockedQueues.get(1).add(klt);
					}
					case IO3: {
						blockedQueues.get(2).add(klt);
					}
				}
			}
		}
	}
	
	public ArrayList<Queue<KernelLevelThread>> getBlockedQueues() {
		return blockedQueues;
	}
	
	public Queue<KernelLevelThread> getReadyQueue() {
		return readyQueue;
	}

}
