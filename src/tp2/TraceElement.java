package tp2;

import java.util.*;

public class TraceElement {
	
	private Integer processID;
	private Integer kltID;
	private Integer ultID;
	private Integer coreID;
	private Integer deviceID;

	public TraceElement(int processID, int kltID) {
		this.processID = processID;
		this.kltID = kltID;
	}
	
	public void setUlt(int ultID) {
		this.ultID = ultID;
	}
	
	public void setCore(int coreID) {
		this.coreID = coreID;
	}
	
	public void setDevice(int deviceID) {
		this.deviceID = deviceID;
	}
	
	public void build(String[][] matrix, int col, HashMap<Integer, Integer> truePositionsMap) {
		int row;
		if (ultID != null)
			row = truePositionsMap.get(ultID);
		else
			row = truePositionsMap.get(kltID);
		String s;
		if (coreID != null)
			s = coreID.toString();
		else {
			String letters = new String("ABCDEFGHIJKLMNOPQRSTUVXYZ");
			Character c = letters.charAt(deviceID - 1);
			s = c.toString();
		}
		matrix[row][col] = s;
	}
	
	public Integer getProcessID() {
		return processID;
	}
	
}
