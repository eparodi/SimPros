package ProcessManager;


import ProcessManager.Exceptions.NotRunneableThread;
import java.util.*;

public class KLT {
    private int processID; //Ya que no existe el proceso, al menos dejo un identificador

    protected int respawn; //El momento que el proceso respawnea VER QUE ES EL MISMO PARA TODOs LOS PROCESOS
    private ArrayList<ULT> ultList; //Un KLT minimamnet eva tenr un ULT, esta es la lista que las contiene de manera desordenada
    protected Enum.ThreadState state;
    private int runningUserThread;

    public KLT(int processID, ArrayList<ULT> ultList) {
        this.processID=processID;
        this.ultList=ultList;
    }

    public void run() throws NotRunneableThread {
        if(state!=Enum.ThreadState.READY){
            throw new NotRunneableThread();
        }
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run finished KLT");
        }

        //continua aca

    }

    public boolean isFinished(){
        for(ULT u: ultList){
            if(u.runningTask !=-1){
                return false;
            }
        }
        return true;
    }
}
