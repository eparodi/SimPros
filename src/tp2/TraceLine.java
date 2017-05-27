package tp2;

import java.util.*;

public class TraceLine {
	
	private ArrayList<TraceElement> elementArray;
	
	public TraceLine(int time) {
		elementArray = new ArrayList<>();
	}
	
	public void insertElement(TraceElement element) {
		elementArray.add(element);
	}
	
	public void build(String[][] matrix, int col, HashMap<Integer, Integer> truePositionsMap) {
		for (TraceElement element : elementArray) {
			element.build(matrix, col, truePositionsMap);
		}
	}

}
