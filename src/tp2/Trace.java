package tp2;

import java.util.*;

public class Trace {
	
	private ArrayList<TraceLine> lineArray;
	
	public Trace() {
		lineArray = new ArrayList<>();
	}
	
	public void insertLine(TraceLine line) {
		lineArray.add(line);
	}
	
	public void print() {
		System.out.println("Trace:");
		System.out.println("");
		for (TraceLine line : lineArray) {
			line.print();
		}
	}

}
