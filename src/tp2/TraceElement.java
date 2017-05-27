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
	
	public void build(Character[][] matrix, int col, HashMap<Integer, Integer> truePositionsMap) {
		int row;
		if (ultID != null)
			row = truePositionsMap.get(ultID);
		else
			row = truePositionsMap.get(kltID);
		Character c = new Character('o');
		if (coreID != null) {
			switch (coreID) {
				case 1: {
					c = new Character('1');
					break;
				}
				case 2: {
					c = new Character('2');
					break;
				}
				case 3: {
					c = new Character('3');
					break;
				}
				case 4: {
					c = new Character('4');
					break;
				}
				case 5: {
					c = new Character('5');
				}
			}
		}
		else {
			switch (deviceID) {
				case 1: {
					c = new Character('A');
					break;
				}
				case 2: {
					c = new Character('B');
					break;
				}
				case 3: {
					c = new Character('C');
					break;
				}
				case 4: {
					c = new Character('D');
					break;
				}
				case 5: {
					c = new Character('E');
					break;
				}
				case 6: {
					c = new Character('F');
					break;
				}
			}
		}
		matrix[row][col] = c;
	}
	
	public void print() {
		System.out.print("( " + "P:" + processID + ", K:" + kltID);
		if (ultID != null) {
			System.out.print(", U:" + ultID);
		}
		if (coreID != null) {
			System.out.print(", C:" + coreID);
		}
		if (deviceID != null) {
			System.out.print(", D:" + deviceID);
		}
		System.out.print(" )");
	}
	
}
