package tp2;

public class TraceManager {
	
	private Trace trace;
	private TraceLine traceLine;
	
	public TraceManager() {
		trace = new Trace();
	}
	
	public void print() {
		trace.print();
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
	
	public void insertElement(KernelLevelThread klt, int device) {
		TraceElement element = new TraceElement(klt.getProcessID(), klt.getID());
		element.setDevice(device);
		UserLevelThread ult = klt.getRunningUlt();
		if (ult != null)
			element.setUlt(ult.getID());
		traceLine.insertElement(element);
	}

}
