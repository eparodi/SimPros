package tp2;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Core> coresArray = new ArrayList<>();
		coresArray.add(new Core(1));
		coresArray.add(new Core(2));
		
		ArrayList<Task> taskArray11 = new ArrayList<>();
		taskArray11.add(new Task(3));
		taskArray11.add(new Task(1, 1));
		taskArray11.add(new Task(2));
		KernelLevelThread klt11 = new KernelLevelThread(taskArray11, 11, 1);
		
		ArrayList<Task> taskArray12 = new ArrayList<>();
		taskArray12.add(new Task(2));
		taskArray12.add(new Task(2, 2));
		taskArray12.add(new Task(3));
		KernelLevelThread klt12 = new KernelLevelThread(taskArray12, 12, 1);
		
		ArrayList<Task> taskArray13 = new ArrayList<>();
		taskArray13.add(new Task(1));
		taskArray13.add(new Task(3));
		taskArray13.add(new Task(2, 1));
		taskArray13.add(new Task(2));
		KernelLevelThread klt13 = new KernelLevelThread(taskArray13, 13, 1);
		
		ArrayList<KernelLevelThread> kltArray1 = new ArrayList<>();
		kltArray1.add(klt11);
		kltArray1.add(klt12);
		kltArray1.add(klt13);
		
		Process process1 = new Process(kltArray1, 1, 0);
		
		
		ArrayList<Task> taskArray21 = new ArrayList<>();
		taskArray21.add(new Task(2));
		taskArray21.add(new Task(2));
		taskArray21.add(new Task(1, 2));
		taskArray21.add(new Task(1));
		KernelLevelThread klt21 = new KernelLevelThread(taskArray21, 21, 2);
		
		ArrayList<Task> taskArray22 = new ArrayList<>();
		taskArray22.add(new Task(2));
		taskArray22.add(new Task(3, 1));
		taskArray22.add(new Task(1));
		KernelLevelThread klt22 = new KernelLevelThread(taskArray22, 22, 2);
		
		ArrayList<Task> taskArray23 = new ArrayList<>();
		taskArray23.add(new Task(1));
		taskArray23.add(new Task(1, 1));
		taskArray23.add(new Task(3));
		taskArray23.add(new Task(2, 3));
		taskArray23.add(new Task(1));
		KernelLevelThread klt23 = new KernelLevelThread(taskArray23, 23, 2);
		
		ArrayList<Task> taskArray24 = new ArrayList<>();
		taskArray24.add(new Task(4));
		taskArray24.add(new Task(1));
		taskArray24.add(new Task(3, 2));
		taskArray24.add(new Task(2));
		KernelLevelThread klt24 = new KernelLevelThread(taskArray24, 24, 2);
		
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
		
		ArrayList<KernelLevelThread> kltArray3 = new ArrayList<>();
		kltArray3.add(klt31);
		
		Process process3 = new Process(kltArray3, 3, 0);
		
		ArrayList<Process> processArray = new ArrayList<>();
		processArray.add(process1);
		processArray.add(process2);
		processArray.add(process3);
		
		Scheduler scheduler = new Scheduler(coresArray, processArray, 3);
		TraceManager tManager = new TraceManager();
		scheduler.run(tManager);
	}
	
}
