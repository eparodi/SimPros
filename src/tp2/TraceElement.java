package tp2;

public class TraceElement {
	
	private int processID;
	private int kltID;
	private int ultID;
	private int coreID;
	private int deviceID;

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
	
	public void print() {
		System.out.print("( " + "P:" + processID + ", K:" + kltID);
		if (ultID != 0) {
			System.out.print(", U:" + ultID);
		}
		if (coreID != 0) {
			System.out.print(", C:" + coreID);
		}
		if (deviceID != 0) {
			System.out.print(", D:" + deviceID);
		}
		System.out.print(" )");
	}
	
}
