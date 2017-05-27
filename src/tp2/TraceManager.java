package tp2;

import java.util.HashMap;

public class TraceManager {
	
	private Trace trace;
	private TraceLine traceLine;
	private GanttBuilder gBuilder;
	
	public TraceManager() {
		trace = new Trace();
		gBuilder = new GanttBuilder();
	}
	
	public Trace getTrace() {
		return trace;
	}
	
	public void createLine(int time) {
		traceLine = new TraceLine(time);
	}
	
	public void insertLine() {
		trace.insertLine(traceLine);
		traceLine = null;
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
	
	public void insertGanttLine(GanttLine gLine) {
		gBuilder.insertGanttLine(gLine);
	}
	
	public void setGantt(int rows, HashMap<Integer, Integer> truePositionsMap) {
		gBuilder.setGantt(rows, truePositionsMap, trace);
	}
	
	public void printGantt() {
		gBuilder.printGantt();
	}
	
	public void printGanttLine(int index) {
		gBuilder.printGanttLine(index);
	}

}
