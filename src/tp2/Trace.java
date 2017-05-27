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
	
	public int getLineCount() {
		return lineArray.size();
	}
	
	public void build(String[][] matrix, HashMap<Integer, Integer> truePositionsMap) {
		for (int i = 0; i < lineArray.size(); i++) {
			lineArray.get(i).build(matrix, i, truePositionsMap);
		}
	}

}
