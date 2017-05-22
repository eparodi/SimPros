package tp2;

import java.util.*;

public class TraceManager {
	
	private Trace trace;
	private Character[][] matrix;
	private TraceLine traceLine;
	private int rows;
	private int cols;
	
	public TraceManager() {
		trace = new Trace();
	}
	
	public void print() {
		trace.print();
	}
	
	public void printGantt() {
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j ++) {
				if (matrix[i][j] == null)
					System.out.print(" |");
				else
					System.out.print(matrix[i][j] + "|");
			}
			System.out.println("");
		}
	}
	
	public void buildGantt(int rows, HashMap<Integer, Integer> truePositionsMap) {
		this.rows = rows;
		this.cols = trace.getLineCount();
		matrix = new Character[rows][cols];
		trace.build(matrix, truePositionsMap);
	}
	
	public void createLine(int time) {
		traceLine = new TraceLine(time);
	}
	
	public void insertLine() {
		trace.insertLine(traceLine);
		traceLine = null;
	}
	
	public void insertElement(KernelLevelThread klt, Core core) {
		TraceElement element = new TraceElement(klt.getProcessID(), klt.getID());
		element.setCore(core.getID());
		UserLevelThread ult = klt.getRunningUlt();
		if (ult != null)
			element.setUlt(ult.getID());
		traceLine.insertElement(element);
	}
	
	public void insertElement(TraceElement element) {
		traceLine.insertElement(element);
	}
	
	public void insertElement(KernelLevelThread klt, int device) {
		TraceElement element = new TraceElement(klt.getProcessID(), klt.getID());
		element.setDevice(device);
		UserLevelThread ult = klt.getRunningUlt();
		if (ult != null)
			element.setUlt(ult.getID());
		traceLine.insertElement(element);
	}

}
