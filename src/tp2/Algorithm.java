package tp2;

import java.util.ArrayList;

public interface Algorithm {
	
	public UserLevelThread getFirst();
	
	public UserLevelThread update(UserLevelThread ult);
	
	public UserLevelThread pickNext(UserLevelThread ult);
	
	public void updateNewUlts();

	public ArrayList<UserLevelThread> getUltArray();

}