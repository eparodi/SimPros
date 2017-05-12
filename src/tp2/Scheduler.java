package tp2;

import java.util.*;

import tp2.UserLevelThread.ThreadState;

public class Scheduler {

	private Queue<Process> newProcessesQueue;
	private Queue<Process> readyProcessesQueue;
	private HashMap<Integer, Process> blockedProcessesMap;
	private ArrayList<Process> runningProcessesArray;
	private HashMap<Integer, Queue<KernelLevelThread>> coresQueuesMap;
	private HashMap<Integer, Queue<KernelLevelThread>> readyKltQueuesMap;
	private HashMap<Integer, Core> appropriatedMap;
	private ArrayList<Core> coresArray;
	private Queue<Core> availableCoresQueue;
	private IoManager ioManager;
	private int timeCounter;
	
	public Scheduler(ArrayList<Core> coresArray, ArrayList<Process> allProcesses, int deviceCount) {
		newProcessesQueue = new LinkedList<>();
		readyProcessesQueue = new LinkedList<>();
		readyKltQueuesMap = new HashMap<>();
		for (Process process : allProcesses) {
			if (process.getStartTime() != 0)
				newProcessesQueue.offer(process);
			readyProcessesQueue.offer(process);
			ArrayList<KernelLevelThread> kltArray = process.getKernelThreadsArray();
			Queue<KernelLevelThread> kltQueue = new LinkedList<>();
			for (KernelLevelThread klt : kltArray) {
				kltQueue.offer(klt);
			}
			readyKltQueuesMap.put(process.getID(), kltQueue);
		}
		this.coresArray = coresArray;
		availableCoresQueue = new LinkedList<Core>();
		coresQueuesMap = new HashMap<>();
		for (Core core : coresArray) {
			coresQueuesMap.put(core.getID(), new LinkedList<>());
			availableCoresQueue.offer(core);
		}
		blockedProcessesMap = new HashMap<>();
		runningProcessesArray = new ArrayList<>();
		appropriatedMap = new HashMap<>();
		ioManager = new IoManager(this, deviceCount);
		timeCounter = 0;
	}
	
	public void run(TraceManager tManager) {
		while (!finished()) {
			
			tManager.createLine(timeCounter);
			/*
			 * First we situate every possible kernel thread to a core or the corresponding io queue.
			 * We start by checking the processes that have at least a thread running in a core.
			 */
			if (!runningProcessesArray.isEmpty() && !availableCoresQueue.isEmpty()) {
				for (Process process : runningProcessesArray) {
					situateInCores(process);
				}
			}
			// Then, if there is a least one core free, we check the queue of ready processes and do the same as before.
			while (!availableCoresQueue.isEmpty() && !readyProcessesQueue.isEmpty()) {
				Process process = readyProcessesQueue.poll();
				situateInCores(process);
			}
			/*
			 * Now we continue by running the ioManager, executing every requested device.
			 * If a thread is unblocked, it returns to the readyQueue of the corresponding process.
			 */
			ioManager.runBlockedThreads(tManager);
			/*
			 * Finally, we run the threads in the cores, and depending of the resulting state of the thread,
			 * we place them in one place or another.
			 */
			for (Core core : coresArray) {
				KernelLevelThread klt = core.getRunningKlt();
				if (klt != null) {
					klt.run();
					tManager.insertElement(klt, core);
					UserLevelThread.ThreadState state = klt.getState();
					switch (state) {
						case NONBLOCKED: {
							/*
							 * There is no need of putting it out of the core.
							 */
							break;
						}
						case BLOCKED: {
							checkProcessState(klt);
							updateCoreQueue(core);
							ioManager.insertBlockedThread(klt);
							break;
						}
						case FINISHED: {
							checkProcessState(klt);
							updateCoreQueue(core);
						}
					}
				}
			}
			tManager.insertLine();
			timeCounter ++;
		}
		tManager.print();
	}
	
	public void insertThreadFromIo(KernelLevelThread klt) {
		int processID = klt.getProcessID();
		Process process = blockedProcessesMap.get(processID);
		if (process != null) {
			blockedProcessesMap.remove(processID);
			readyProcessesQueue.offer(process);
		}
		readyKltQueuesMap.get(processID).offer(klt);
	}
	
	private void situateInCores(Process process) {
		Queue<KernelLevelThread> readyQueue = readyKltQueuesMap.get(process.getID());
		while (!availableCoresQueue.isEmpty() && !readyQueue.isEmpty()) {
			KernelLevelThread klt = readyQueue.poll();
			if (klt.getState() == UserLevelThread.ThreadState.BLOCKED) {
				throw new RuntimeException("A blocked process was picked from a ready queue");
			}
			else {
				Core core = appropriatedMap.get(klt.getID());
				if (core != null) {
					if (core.isAvailable()) {
						core.setRunningKlt(klt);
						availableCoresQueue.remove(core);
					}
					else
						coresQueuesMap.get(core.getID()).offer(klt);
				}
				else {
					Core avCore = availableCoresQueue.poll();
					avCore.setRunningKlt(klt);
					appropriatedMap.put(klt.getID(), avCore);
				}
			}
		}
		if (readyQueue.isEmpty() && process.getState() == UserLevelThread.ThreadState.BLOCKED)
			blockedProcessesMap.put(process.getID(), process);
		else {
			if (!containsProcess(runningProcessesArray, process))
				runningProcessesArray.add(process);
		}
	}
	
	private void checkProcessState(KernelLevelThread klt) {
		for (Iterator<Process> iterator = runningProcessesArray.iterator(); iterator.hasNext();) {
			Process process = iterator.next();
			if (process.getID() == klt.getProcessID()) {
				if (process.getState() == UserLevelThread.ThreadState.BLOCKED) {
					iterator.remove();
					blockedProcessesMap.put(process.getID(), process);
					return;
				}
				else if (process.getState() == ThreadState.FINISHED)
					iterator.remove();
			}
		}
		return;
	}
	
	private void updateCoreQueue(Core core) {
		Queue<KernelLevelThread> coreQueue = coresQueuesMap.get(core.getID());
		if (coreQueue.isEmpty()) {
			core.setRunningKlt(null);
			availableCoresQueue.offer(core);
		}
		else {
			core.setRunningKlt(coreQueue.poll());
		}
		return;
	}
	
	private boolean containsProcess(ArrayList<Process> processArray, Process process) {
		for (Process p : processArray) {
			if (p.getID() == process.getID())
				return true;
		}
		return false;
	}
	
	private boolean finished() {
		return newProcessesQueue.isEmpty() && readyProcessesQueue.isEmpty() && blockedProcessesMap.isEmpty() && runningProcessesArray.isEmpty();
	}
	
}
