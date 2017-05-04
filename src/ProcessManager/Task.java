package ProcessManager;

public class Task {

	public static enum Type {
		CPU, IO
	}
	public static enum Device {
		IO1, IO2, IO3
	}
	
	private int amount;
	private Type type;
	private Device divice;
	
	public Task(int amount, Device divice) {
		this.amount = amount;
		this.type = Type.IO;
		this.divice = divice;
	}
	
	public Task(int amount) {
		this.amount = amount;
		this.type = Type.CPU;
	}
	
	public void run() {
		amount = amount - 1;
	}
	
	public boolean isFinished() {
		if (amount > 0)
			return false;
		return true;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public Type getType() {
		return type;
	}
	
	public Device getDevice() {
		return divice;
	}
	
}
