package tp2;

public class Task {

	public static enum Type {
		CPU, IO
	};
	
	private int amount;
	private Type type;
	private int divice;
	
	public Task(int amount, int divice) {
		this.amount = amount;
		this.type = Type.IO;
		this.divice = divice;
	}
	
	public Task(int amount) {
		this.amount = amount;
		this.type = Type.CPU;
	}
	
	public void run() {
		if ((amount - 1) < 0)
			throw new RuntimeException("A task was finished and tried to run");
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
	
	public int getDevice() {
		return divice;
	}
	
}
