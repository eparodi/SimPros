package tp2;

import java.util.*;

public class TraceLine {
	
	private int time;
	private ArrayList<TraceElement> elementArray;
	
	public TraceLine(int time) {
		this.time = time;
		elementArray = new ArrayList<>();
	}
	
	public void insertElement(TraceElement element) {
		elementArray.add(element);
	}
	
	public void print() {
		if (time < 10)
			System.out.print("Time: " + time + ":  [  ");
		else if (time > 9 && time < 100)
			System.out.print("Time: " + time + ": [  ");
		else if (time > 99)
			System.out.print("Time: " + time + ":[  ");
		for (TraceElement element : elementArray) {
			element.print();
			System.out.print(" ");
		}
		System.out.println(" ]");
	}

}
