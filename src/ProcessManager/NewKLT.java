package ProcessManager;


import ProcessManager.Exceptions.NotRunneableThread;

import java.util.*;

public class NewKLT {
    private int processID; //Ya que no existe el rpoceso, al menos dejo un identificador

    protected int respawn; //El momento que el proceso respawnea
    private ArrayList<NewULT> ultList; //Un KLT minimamnet eva tenr un ULT, esta es la lista que las contiene de manera desordenada
    protected Enum.ThreadState state;
    private int runningUserThread;

    public NewKLT(int processID,ArrayList<NewULT> ultList) {
        this.processID=processID;
        this.ultList=ultList;
    }

    public void run() throws Exception {
        if(state!=Enum.ThreadState.READY){
            throw new NotRunneableThread();
        }
    }

    public boolean isFinished(){
        for(NewULT u: ultList){
            if(u.runningTask !=-1){
                return false;
            }
        }
        return true;
    }
}
