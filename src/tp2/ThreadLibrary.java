package tp2;
import java.util.*;

public class ThreadLibrary {
	
	private UserLevelThread runningUlt;
	private Algorithm algorithm;
	
	public ThreadLibrary(ArrayList<UserLevelThread> ultArray, int algorithm, long quantum) {
		switch(algorithm) {
			case 0: {
				this.algorithm = new FIFO(ultArray);
				break;
			}
			case 1: {
				this.algorithm = new RR(ultArray, quantum);
				break;
			}
			case 2: {
				this.algorithm = new SPN(ultArray);
				break;
			}
			case 3:	{
				this.algorithm = new SRT(ultArray);
				break;
			}
			case 4:	{
				this.algorithm = new HRRN(ultArray);
				break;
			}
		}
		runningUlt = this.algorithm.getFirst();
		if (runningUlt == null) {
			throw new RuntimeException("A thread library initialized a null first ult.");
		}
	}
	
	public UserLevelThread.ThreadState getState() {
		if (runningUlt == null)
			return UserLevelThread.ThreadState.FINISHED;
		return runningUlt.getState();
	}
	
	public Integer getDevice() {
		return runningUlt.getDevice();
	}
	
	public UserLevelThread getRunningUlt() {
		return runningUlt;
	}
	
	public void run(TraceElement element) {
		if (runningUlt == null) {
			throw new RuntimeException("A thread library tried to run a null ult.");
		}
		if (runningUlt.getState() == UserLevelThread.ThreadState.NONBLOCKED) {
			runningUlt = algorithm.pickNext(runningUlt);
			element.setUlt(runningUlt.getID());
			runningUlt.run();
			runningUlt = algorithm.update(runningUlt);
			algorithm.updateNewUlts();
		}
		else if (runningUlt.getState() == UserLevelThread.ThreadState.BLOCKED) {
			element.setUlt(runningUlt.getID());
			runningUlt.run();
		}
	}

	public ArrayList<UserLevelThread> getUltArray() {
		return algorithm.getUltArray();
	}
	
}