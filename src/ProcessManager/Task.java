package ProcessManager;
public class Task {

	
	protected int amount;
    protected Enum.Type type;
    protected Enum.Device device;
    protected Enum.Job  job;

    public Task(int amount, Enum.Job job){
        this.amount=amount;
        this.job=job;
    }

    @Deprecated
	public Task(int amount, Enum.Device device) {
		this.amount = amount;
		this.type = Enum.Type.IO;
		this.device = device;

	}

	@Deprecated
	public Task(int amount) {
		this.amount = amount;
		this.type = Enum.Type.CPU;
	}
	
	public void run() {
	    amount = amount - 1;
	}
	
	public boolean isFinished() {
		if (amount > 0)
			return false;
		return true;
	}
	@Deprecated
	public int getAmount() {
		return amount;
	}
	@Deprecated
	public Enum.Type getType() {
		return type;
	}
	@Deprecated
	public Enum.Device getDevice() {
		return device;
	}


}
