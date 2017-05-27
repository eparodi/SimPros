package tp2;

import java.util.ArrayList;
import java.util.HashMap;

public class Example {
	
	HashMap<Integer, Integer> truePositionsMap;
	ArrayList<Core> coresArray;
	ArrayList<Process> processArray;
	int deviceCount;
	int rows;
	
	public Example() {}
	
	public void setExample1() {
		truePositionsMap = new HashMap<>();
		deviceCount = 3;
		rows = 8;
		
		coresArray = new ArrayList<>();
		coresArray.add(new Core(1));
		coresArray.add(new Core(2));
		
		ArrayList<Task> taskArray11 = new ArrayList<>();
		taskArray11.add(new Task(3));
		taskArray11.add(new Task(1, 1));
		taskArray11.add(new Task(2));
		KernelLevelThread klt11 = new KernelLevelThread(taskArray11, 11, 1);
		truePositionsMap.put(11, 0);
		
		ArrayList<Task> taskArray12 = new ArrayList<>();
		taskArray12.add(new Task(2));
		taskArray12.add(new Task(2, 2));
		taskArray12.add(new Task(3));
		KernelLevelThread klt12 = new KernelLevelThread(taskArray12, 12, 1);
		truePositionsMap.put(12, 1);
		
		ArrayList<Task> taskArray13 = new ArrayList<>();
		taskArray13.add(new Task(4));
		taskArray13.add(new Task(2, 1));
		taskArray13.add(new Task(2));
		KernelLevelThread klt13 = new KernelLevelThread(taskArray13, 13, 1);
		truePositionsMap.put(13, 2);
		
		ArrayList<KernelLevelThread> kltArray1 = new ArrayList<>();
		kltArray1.add(klt11);
		kltArray1.add(klt12);
		kltArray1.add(klt13);
		
		Process process1 = new Process(kltArray1, 1, 0);
		
		
		ArrayList<Task> taskArray21 = new ArrayList<>();
		taskArray21.add(new Task(4));
		taskArray21.add(new Task(1, 2));
		taskArray21.add(new Task(1));
		KernelLevelThread klt21 = new KernelLevelThread(taskArray21, 21, 2);
		truePositionsMap.put(21, 3);
		
		ArrayList<Task> taskArray22 = new ArrayList<>();
		taskArray22.add(new Task(2));
		taskArray22.add(new Task(3, 1));
		taskArray22.add(new Task(1));
		KernelLevelThread klt22 = new KernelLevelThread(taskArray22, 22, 2);
		truePositionsMap.put(22, 4);
		
		ArrayList<Task> taskArray23 = new ArrayList<>();
		taskArray23.add(new Task(1));
		taskArray23.add(new Task(1, 1));
		taskArray23.add(new Task(3));
		taskArray23.add(new Task(2, 3));
		taskArray23.add(new Task(1));
		KernelLevelThread klt23 = new KernelLevelThread(taskArray23, 23, 2);
		truePositionsMap.put(23, 5);
		
		ArrayList<Task> taskArray24 = new ArrayList<>();
		taskArray24.add(new Task(5));
		taskArray24.add(new Task(3, 2));
		taskArray24.add(new Task(2));
		KernelLevelThread klt24 = new KernelLevelThread(taskArray24, 24, 2);
		truePositionsMap.put(24, 6);
		
		ArrayList<KernelLevelThread> kltArray2 = new ArrayList<>();
		kltArray2.add(klt21);
		kltArray2.add(klt22);
		kltArray2.add(klt23);
		kltArray2.add(klt24);
		
		Process process2 = new Process(kltArray2, 2, 0);
		
		
		ArrayList<Task> taskArray31 = new ArrayList<>();
		taskArray31.add(new Task(3));
		taskArray31.add(new Task(2, 3));
		taskArray31.add(new Task(1));
		KernelLevelThread klt31 = new KernelLevelThread(taskArray31, 31, 3);
		truePositionsMap.put(31, 7);
		
		ArrayList<KernelLevelThread> kltArray3 = new ArrayList<>();
		kltArray3.add(klt31);
		
		Process process3 = new Process(kltArray3, 3, 0);
		
		processArray = new ArrayList<>();
		processArray.add(process1);
		processArray.add(process2);
		processArray.add(process3);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setExample2() {
		truePositionsMap = new HashMap<>();
		deviceCount = 3;
		rows = 8;
		
		coresArray = new ArrayList<>();
		coresArray.add(new Core(1));
		//coresArray.add(new Core(2));
		
		ArrayList<Task> taskArray11 = new ArrayList<>();
		taskArray11.add(new Task(3));
		taskArray11.add(new Task(1, 1));
		taskArray11.add(new Task(2));
		KernelLevelThread klt11 = new KernelLevelThread(taskArray11, 11, 1);
		truePositionsMap.put(11, 0);
		
		ArrayList<Task> taskArray12 = new ArrayList<>();
		taskArray12.add(new Task(2));
		taskArray12.add(new Task(2, 2));
		taskArray12.add(new Task(3));
		KernelLevelThread klt12 = new KernelLevelThread(taskArray12, 12, 1);
		truePositionsMap.put(12, 1);
		
		ArrayList<Task> taskArray13 = new ArrayList<>();
		taskArray13.add(new Task(4));
		taskArray13.add(new Task(2, 1));
		taskArray13.add(new Task(2));
		KernelLevelThread klt13 = new KernelLevelThread(taskArray13, 13, 1);
		truePositionsMap.put(13, 2);
		
		ArrayList<KernelLevelThread> kltArray1 = new ArrayList<>();
		kltArray1.add(klt11);
		kltArray1.add(klt12);
		kltArray1.add(klt13);
		
		Process process1 = new Process(kltArray1, 1, 0);
		
		
		ArrayList<Task> taskArray21 = new ArrayList<>();
		taskArray21.add(new Task(4));
		taskArray21.add(new Task(1, 2));
		taskArray21.add(new Task(1));
		KernelLevelThread klt21 = new KernelLevelThread(taskArray21, 21, 2);
		truePositionsMap.put(21, 3);
		
		ArrayList<Task> taskArray22 = new ArrayList<>();
		taskArray22.add(new Task(2));
		taskArray22.add(new Task(3, 1));
		taskArray22.add(new Task(1));
		KernelLevelThread klt22 = new KernelLevelThread(taskArray22, 22, 2);
		truePositionsMap.put(22, 4);
		
		ArrayList<Task> taskArray23 = new ArrayList<>();
		taskArray23.add(new Task(1));
		taskArray23.add(new Task(1, 1));
		taskArray23.add(new Task(3));
		taskArray23.add(new Task(2, 3));
		taskArray23.add(new Task(1));
		KernelLevelThread klt23 = new KernelLevelThread(taskArray23, 23, 2);
		truePositionsMap.put(23, 5);
		
		ArrayList<Task> taskArray24 = new ArrayList<>();
		taskArray24.add(new Task(5));
		taskArray24.add(new Task(3, 2));
		taskArray24.add(new Task(2));
		KernelLevelThread klt24 = new KernelLevelThread(taskArray24, 24, 2);
		truePositionsMap.put(24, 6);
		
		ArrayList<KernelLevelThread> kltArray2 = new ArrayList<>();
		kltArray2.add(klt21);
		kltArray2.add(klt22);
		kltArray2.add(klt23);
		kltArray2.add(klt24);
		
		Process process2 = new Process(kltArray2, 2, 0);
		
		
		ArrayList<Task> taskArray31 = new ArrayList<>();
		taskArray31.add(new Task(3));
		taskArray31.add(new Task(2, 3));
		taskArray31.add(new Task(1));
		KernelLevelThread klt31 = new KernelLevelThread(taskArray31, 31, 3);
		truePositionsMap.put(31, 7);
		
		ArrayList<KernelLevelThread> kltArray3 = new ArrayList<>();
		kltArray3.add(klt31);
		
		Process process3 = new Process(kltArray3, 3, 0);
		
		processArray = new ArrayList<>();
		processArray.add(process1);
		processArray.add(process2);
		processArray.add(process3);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setExample3() {
		truePositionsMap = new HashMap<>();
		deviceCount = 0;
		rows = 5;
		
		coresArray = new ArrayList<>();
		coresArray.add(new Core(1));
		
		ArrayList<Task> taskArray111 = new ArrayList<>();
		taskArray111.add(new Task(3));
		UserLevelThread ult111 = new UserLevelThread(taskArray111, 111, 0);
		truePositionsMap.put(111, 0);
		
		ArrayList<Task> taskArray112 = new ArrayList<>();
		taskArray112.add(new Task(6));
		UserLevelThread ult112 = new UserLevelThread(taskArray112, 112, 2);
		truePositionsMap.put(112, 1);
		
		ArrayList<Task> taskArray113 = new ArrayList<>();
		taskArray113.add(new Task(4));
		UserLevelThread ult113 = new UserLevelThread(taskArray113, 113, 4);
		truePositionsMap.put(113, 2);
		
		ArrayList<Task> taskArray114 = new ArrayList<>();
		taskArray114.add(new Task(5));
		UserLevelThread ult114 = new UserLevelThread(taskArray114, 114, 6);
		truePositionsMap.put(114, 3);
		
		ArrayList<Task> taskArray115 = new ArrayList<>();
		taskArray115.add(new Task(2));
		UserLevelThread ult115 = new UserLevelThread(taskArray115, 115, 8);
		truePositionsMap.put(115, 4);
		
		ArrayList<UserLevelThread> ultArray11 = new ArrayList<>();
		ultArray11.add(ult111);
		ultArray11.add(ult112);
		ultArray11.add(ult113);
		ultArray11.add(ult114);
		ultArray11.add(ult115);
		
		KernelLevelThread klt11 = new KernelLevelThread(ultArray11, 4, 11, 1);
		ArrayList<KernelLevelThread> kltArray1 = new ArrayList<>();
		kltArray1.add(klt11);
		
		Process process1 = new Process(kltArray1, 1, 0);
		processArray = new ArrayList<>();
		processArray.add(process1);
		
		//KernelLevelThread(ArrayList<UserLevelThread> ultArray, int algorithm, int ID, int processID)
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public HashMap<Integer, Integer> getTruePositionsMap() {
		return truePositionsMap;
	}

	public ArrayList<Core> getCoresArray() {
		return coresArray;
	}

	public ArrayList<Process> getProcessArray() {
		return processArray;
	}

	public int getDeviceCount() {
		return deviceCount;
	}
	
	public int getRows() {
		return rows;
	}

}
