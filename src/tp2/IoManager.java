package tp2;

import java.util.*;

public class IoManager {
	
	private HashMap<Integer, KernelLevelThread> blockedExecutingThreads;
	private ArrayList<Queue<KernelLevelThread>> blockedQueues;
	private ArrayList<Integer> busyDevices;
	private Scheduler scheduler;
	
	public IoManager(Scheduler scheduler, int deviceCount) {
		this.scheduler = scheduler;
		blockedExecutingThreads = new HashMap<>();
		blockedQueues = new ArrayList<>();
		for(int i = 0; i < deviceCount; i++) {
			blockedQueues.add(new LinkedList<>());
		}
		busyDevices = new ArrayList<>();
	}
	
	public void insertBlockedThread(KernelLevelThread klt) { 
		int device = klt.getDivice();
		if (!blockedExecutingThreads.containsKey(device)) {
			blockedExecutingThreads.put(device, klt);
			busyDevices.add(device);
		}
		else
			blockedQueues.get(device - 1).offer(klt);
	}
	
	public void runBlockedThreads(TraceManager tManager) {
		for (Iterator<Integer> iterator = busyDevices.iterator(); iterator.hasNext();) {
			Integer device = iterator.next();
			KernelLevelThread klt = blockedExecutingThreads.get(device);
			if (klt == null)
				throw new RuntimeException("A device appears to be busy when it has no executing thread");
			TraceElement element = new TraceElement(klt.getProcessID(), klt.getID());
			klt.run(element);
			element.setDevice(device);
			tManager.insertElement(element);
			UserLevelThread.ThreadState state = klt.getState();
			switch(state) {
				case NONBLOCKED: {
					scheduler.insertThreadFromIo(klt);
					selectNext(device, iterator);
					break;
				}
				case BLOCKED: {
					// DO NOTHING
					break;
				}
				case FINISHED: {
					selectNext(device, iterator);
					break;
				}
			}
		}
	}
	
	private void selectNext(Integer device, Iterator<Integer> iterator) {
		blockedExecutingThreads.remove(device);
		if (blockedQueues.get(device - 1).isEmpty())
			iterator.remove();
		else
			blockedExecutingThreads.put(device, blockedQueues.get(device - 1).poll());
	}
	
	public boolean isEmpty() {
		boolean empty = true;
		for (Queue<KernelLevelThread> blockedQueue : blockedQueues) {
			if (!blockedQueue.isEmpty()) {
				empty = false;
			}
		}
		return empty;
	}
	
}
