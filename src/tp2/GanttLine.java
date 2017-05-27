package tp2;

import java.util.*;

public class GanttLine {
	
	private ArrayList<Process> readyProcessArray;
	private ArrayList<ArrayList<KernelLevelThread>> readyKltArrays;
	private ArrayList<ArrayList<KernelLevelThread>> deviceKltArrays;
	//private ArrayList<ArrayList<UserLevelThread>> readyUltArrays;
	
	public GanttLine(Queue<Process> readyProcessQueue, ArrayList<Process> runningProcessesArray, HashMap<Integer, 
			Queue<KernelLevelThread>> readyKltQueuesMap, ArrayList<Queue<KernelLevelThread>> deviceKltQueues) {
		readyProcessArray = new ArrayList<>();
		for (Iterator<Process> iterator = readyProcessQueue.iterator(); iterator.hasNext();) {
			readyProcessArray.add(iterator.next());
		}
		readyKltArrays = new ArrayList<>();
		for (Process process : runningProcessesArray) {
			Queue<KernelLevelThread> readyKltQueue = readyKltQueuesMap.get(process.getID());
			ArrayList<KernelLevelThread> readyKltArray = new ArrayList<>();
			for (Iterator<KernelLevelThread> iterator = readyKltQueue.iterator(); iterator.hasNext();) {
				readyKltArray.add(iterator.next());
			}
			readyKltArrays.add(readyKltArray);
		}
		for (Process process : readyProcessArray) {
			Queue<KernelLevelThread> readyKltQueue = readyKltQueuesMap.get(process.getID());
			ArrayList<KernelLevelThread> readyKltArray = new ArrayList<>();
			for (Iterator<KernelLevelThread> iterator = readyKltQueue.iterator(); iterator.hasNext();) {
				readyKltArray.add(iterator.next());
			}
			readyKltArrays.add(readyKltArray);
		}
		deviceKltArrays = new ArrayList<>();
		for (Queue<KernelLevelThread> deviceQueue : deviceKltQueues) {
			ArrayList<KernelLevelThread> deviceKltArray = new ArrayList<>();
			for (Iterator<KernelLevelThread> iterator = deviceQueue.iterator(); iterator.hasNext();) {
				deviceKltArray.add(iterator.next());
			}
			deviceKltArrays.add(deviceKltArray);
		}
	}

	public ArrayList<Process> getReadyProcessArray() {
		return readyProcessArray;
	}

	public ArrayList<ArrayList<KernelLevelThread>> getReadyKltArrays() {
		return readyKltArrays;
	}

	public ArrayList<ArrayList<KernelLevelThread>> getDeviceKltArrays() {
		return deviceKltArrays;
	}
	
	public void printGanttLine() {
		System.out.print("Ready Processes: ");
		for (Process process : readyProcessArray) {
			System.out.print(process.getID() + ", ");
		}
		System.out.println("");
	}
}
