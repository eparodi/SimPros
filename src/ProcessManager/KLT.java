package ProcessManager;


import ProcessManager.Exceptions.NotRunneableThread;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;

public abstract class KLT {
    protected int processID; //Ya que no existe el proceso, al menos dejo un identificador
    protected int kltID;
    protected int inCore;
    protected int respawn; //El momento que el proceso respawnea VER QUE ES EL MISMO PARA TODOs LOS PROCESOS
    protected ArrayList<ULT> ultList; //Un KLT minimamnet eva tenr un ULT, esta es la lista que las contiene de manera desordenada
    protected Enum.ThreadState state=Enum.ThreadState.READY;

    public KLT(int processID, int kltID, ArrayList<ULT> ultList) {
        this.processID=processID;
        this.kltID=kltID;
        this.ultList=ultList;
    }

    protected void waiting(){
        for(ULT u: ultList){
            u.waiting();
        }
    }

    public abstract NodeIO run() throws NotRunneableThread, InvalidArgumentException;

    public boolean isFinished(){
        if(ultList.size()==0){
            return true;
        }
        for(ULT u: ultList){
            if(!u.isFinished()){
                return false;
            }
        }
        return true;
    }
}
