package tp2;

public interface Algorithm {
	
	public UserLevelThread getFirst();
	
	public UserLevelThread update(UserLevelThread ult);
	
	public UserLevelThread pickNext(UserLevelThread ult);
	
	public void updateNewUlts();

}