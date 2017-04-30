package tp2;
import java.util.*;

public class Process {
	
	private Queue<KernelLevelThread> ready_queue;
	private ArrayList<Queue<KernelLevelThread>> blocked_queues;
	
	public Process(ArrayList<KernelLevelThread> klt_array, int devices_count) {
		ready_queue = new LinkedList<>();
		blocked_queues = new ArrayList<>();
		for (int i = 0; i < devices_count; i++) {
			blocked_queues.add(new LinkedList<>());
		}
		
		for (KernelLevelThread klt : klt_array) {
			if (klt.getState() == UserLevelThread.ThreadState.NONBLOCKED)
				ready_queue.offer(klt);
			else {
				Task.Device device = klt.getDivice();
				switch(device) {
					case IO1: {
						blocked_queues.get(0).add(klt);
					}
					case IO2: {
						blocked_queues.get(1).add(klt);
					}
					case IO3: {
						blocked_queues.get(2).add(klt);
					}
				}
			}
		}
	}
	
	public ArrayList<Queue<KernelLevelThread>> getBlockedQueues() {
		return blocked_queues;
	}
	
	public Queue<KernelLevelThread> getReadyQueue() {
		return ready_queue;
	}

}
