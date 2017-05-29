package tp2;

import java.util.*;

public class GanttLine {

    // Running
    private ArrayList<Integer> runningProcessesArray;
    private ArrayList<ArrayList<Integer>> runningProcesseskltsArrays;
    private ArrayList<ArrayList<Integer>> runningProcessesUltsArrays;

    // Ready
    private ArrayList<Integer> readyProcessesArray;
    private ArrayList<ArrayList<Integer>> readyProcessesKltsArrays;
    private ArrayList<ArrayList<Integer>> readyProcessesUltsArrays;

    private ArrayList<ArrayList<Integer>> devicesKltsArrays;
    private ArrayList<ArrayList<Integer>> coresKltsArrays;
	
	public GanttLine(Queue<Process> readyProcessQueue, ArrayList<Process> runningProcessesArray, HashMap<Integer, 
			Queue<KernelLevelThread>> readyKltQueuesMap, ArrayList<Queue<KernelLevelThread>> deviceKltQueues,
                     ArrayList<Core> coresArray, HashMap<Integer, Queue<KernelLevelThread>> coresQueuesMap) {
        // Running Processes
        this.runningProcessesArray = new ArrayList<>();
        for (Process process : runningProcessesArray) {
            this.runningProcessesArray.add(process.getID());
        }
        // Running Processes Klt & Ult Queues
        runningProcesseskltsArrays = new ArrayList<>();
        runningProcessesUltsArrays = new ArrayList<>();
        for (Integer id : this.runningProcessesArray) {
            ArrayList<Integer> kltArray = new ArrayList<>();
            Queue<KernelLevelThread> kltQueue = readyKltQueuesMap.get(id);
            for (KernelLevelThread klt : kltQueue) {
                ThreadLibrary tLibrary = klt.getThreadLibrary();
                if (tLibrary != null) {
                    ArrayList<Integer> ultArray = new ArrayList<>();
                    ArrayList<UserLevelThread> ults = tLibrary.getUltArray();
                    for (UserLevelThread ult : ults) {
                        ultArray.add(ult.getID());
                    }
                    runningProcessesUltsArrays.add(ultArray);
                }
                kltArray.add(klt.getID());
            }
            runningProcesseskltsArrays.add(kltArray);
        }

        // Ready Processes
        readyProcessesArray = new ArrayList<>();
        for (Process process : readyProcessQueue) {
            readyProcessesArray.add(process.getID());
        }
        // Ready Processes Klt Queues
        readyProcessesKltsArrays = new ArrayList<>();
        readyProcessesUltsArrays = new ArrayList<>();
        for (Integer id : readyProcessesArray) {
            ArrayList<Integer> kltArray = new ArrayList<>();
            Queue<KernelLevelThread> kltQueue = readyKltQueuesMap.get(id);
            for (KernelLevelThread klt : kltQueue) {
                ThreadLibrary tLibrary = klt.getThreadLibrary();
                if (tLibrary != null) {
                    ArrayList<Integer> ultArray = new ArrayList<>();
                    ArrayList<UserLevelThread> ults = tLibrary.getUltArray();
                    for (UserLevelThread ult : ults) {
                        ultArray.add(ult.getID());
                    }
                    readyProcessesUltsArrays.add(ultArray);
                }
                kltArray.add(klt.getID());
            }
            readyProcessesKltsArrays.add(kltArray);
        }

        // Devices Klt Queues
        devicesKltsArrays = new ArrayList<>();
        for (Queue<KernelLevelThread> kltQueue : deviceKltQueues) {
            ArrayList<Integer> kltArray = new ArrayList<>();
            for (KernelLevelThread klt : kltQueue) {
                kltArray.add(klt.getID());
            }
            devicesKltsArrays.add(kltArray);
        }

        // Cores Klt Queues
        coresKltsArrays = new ArrayList<>();
        for (Core core : coresArray) {
            ArrayList<Integer> kltArray = new ArrayList<>();
            Queue<KernelLevelThread> kltQueue = coresQueuesMap.get(core.getID());
            for (KernelLevelThread klt : kltQueue) {
                kltArray.add(klt.getID());
            }
            coresKltsArrays.add(kltArray);
        }
	}

	public void print() {
        // Running Processes
        System.out.println("");
        System.out.print("Running Processes: ");
        for (Integer id : runningProcessesArray) {
            System.out.print(id + ", ");
        }
        System.out.println("");

        // Running Processes Klts
        System.out.println("Running Processes Klt Queues: ");
        for (ArrayList<Integer> kltArray : runningProcesseskltsArrays) {
            printKlt(kltArray);
        }

        // Running Processes Ults
        System.out.println("Running Processes Ult Queues");
        for (ArrayList<Integer> ultArray : runningProcessesUltsArrays) {
            printUlt(ultArray);
        }
        System.out.println("");

        // Ready Processes
        System.out.println("");
        System.out.print("Ready Processes: ");
        for (Integer id : readyProcessesArray) {
            System.out.print(id + ", ");
        }
        System.out.println("");

        // Ready Processes Klts
        System.out.println("Ready Processes Klt Queues: ");
        for (ArrayList<Integer> kltArray : readyProcessesKltsArrays) {
            printKlt(kltArray);
        }

        // Ready Processes Ults
        System.out.println("Ready Processes Ult Queues");
        for (ArrayList<Integer> ultArray : readyProcessesKltsArrays) {
            printUlt(ultArray);
        }
        System.out.println("");

        // Cores Klts
        System.out.println("Cores Klt Queues");
        for (ArrayList<Integer> kltArray : coresKltsArrays) {
            printKlt(kltArray);
        }
        System.out.println("");

        // Devices Klts
        System.out.println("Devices Klt Queues");
        for (ArrayList<Integer> kltArray : devicesKltsArrays) {
            printKlt(kltArray);
        }
        System.out.println("");
    }

    private void printKlt(ArrayList<Integer> kltArray) {
        System.out.print("Klt Queue: ");
        for (Integer id : kltArray) {
            System.out.print(id + ", ");
        }
        System.out.println("");
    }

    private void printUlt(ArrayList<Integer> ultArray) {
        System.out.print("Ult Queue: ");
        for (Integer id : ultArray) {
            System.out.print(id + ", ");
        }
        System.out.println("");
    }

    public ArrayList<Integer> getRunningProcessesArray() {
        return runningProcessesArray;
    }

    public ArrayList<ArrayList<Integer>> getRunningProcesseskltsArrays() {
        return runningProcesseskltsArrays;
    }

    public ArrayList<Integer> getReadyProcessesArray() {
        return readyProcessesArray;
    }

    public ArrayList<ArrayList<Integer>> getReadyProcessesKltsArrays() {
        return readyProcessesKltsArrays;
    }

    public ArrayList<ArrayList<Integer>> getDevicesKltsArrays() {
        return devicesKltsArrays;
    }

    public ArrayList<ArrayList<Integer>> getCoresKltsArrays() {
        return coresKltsArrays;
    }

    public ArrayList<ArrayList<Integer>> getRunningProcessesUltsArrays() {
        return runningProcessesUltsArrays;
    }

    public ArrayList<ArrayList<Integer>> getReadyProcessesUltsArrays() {
        return readyProcessesUltsArrays;
    }

}
