package ProcessManager;


import java.util.*;

public class NewKLT {
    private ArrayList<NewULT> ultList;
    private Enum.ThreadState state;
    private int runningUserThread;

    public NewKLT(ArrayList<NewULT> ultList) {
        this.ultList=ultList;
    }

    public void run() throws Exception {
        if(state!=Enum.ThreadState.READY){
            throw new Exception("Ala akbar"); //TODO: CREA UNA EXCEPTION;
        }

    }

    public boolean isFinished(){
        for(NewULT u: ultList){
            if(u.running_task!=-1){
                return false;
            }
        }
        return true;
    }
}
