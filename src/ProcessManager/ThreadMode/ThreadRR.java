package ProcessManager.ThreadMode;


import ProcessManager.Enum;
import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.KLT;
import ProcessManager.Response.Response;
import ProcessManager.ULT;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRR extends KLT{
    private Queue<ULT> readyQueue;
    private int usedQuantum=0; //es lo que usa en la ejecucion
    private int quantum; //Es el definido

    public ThreadRR(int processID, ArrayList<ULT> ultList, int quantum) {
        super(processID, ultList);
        readyQueue=new LinkedBlockingQueue<>(ultList);
        this.quantum=quantum;
    }

    public Response run() throws NotRunneableThread {
        if(state!= Enum.ThreadState.READY){
            throw new NotRunneableThread();
        }
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run finished KLT");
        }
        waiting();
        ULT toRunNow=readyQueue.peek();
        usedQuantum++;
        if(toRunNow==null){
            throw new NotRunneableThread("ReadyQueue is empty");
        }
        Response resp=toRunNow.run();
        if(resp.typeJob!= Enum.Job.CPU){
            state=Enum.ThreadState.BLOCKED;
        }
        if(toRunNow.isFinished()){
            readyQueue.remove();
            usedQuantum=0;
        }
        if(quantum==usedQuantum){
            readyQueue.remove();
            readyQueue.add(toRunNow);
        }
        return resp;
    }
}
