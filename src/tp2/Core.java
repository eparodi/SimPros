package tp2;

import java.util.*;

public class Core {

	private KernelLevelThread runningKlt;
	private int ID;
	
	public Core(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
	public KernelLevelThread getRunningKlt() {
		return runningKlt;
	}
	
	public boolean isAvailable() {
		if (runningKlt == null)
			return true;
		return false;
	}
	
	public void setRunningKlt(KernelLevelThread klt) {
		runningKlt = klt;
	}
	
	public void run() {
		if (runningKlt == null)
			throw new RuntimeException("A core tried to run its runningKlt when it was null");
		runningKlt.run();
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !o.getClass().equals(getClass()))
			return false;
		Core core = (Core) o;
		if (this.ID != (core.getID()))
			return false;
		return true;
	}
}
