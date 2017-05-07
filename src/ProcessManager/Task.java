package ProcessManager;

public class Task {

	protected int amount;
    protected Enum.Job  job;

    public Task(int amount, Enum.Job job){
        this.amount=amount;
        this.job=job;
    }

	public boolean isFinished() {
		if (amount > 0)
			return false;
		return true;
	}


}
