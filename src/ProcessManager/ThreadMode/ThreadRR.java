package ProcessManager.ThreadMode;


import ProcessManager.Enum;
import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.KLT;
import ProcessManager.NodeIO;
import ProcessManager.Response.Response;
import ProcessManager.ULT;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRR extends KLT{
    private Queue<ULT> readyQueue;
    private int usedQuantum=0; //es lo que usa en la ejecucion
    private int quantum; //Es el definido

    public ThreadRR(int processID, int kltID, ArrayList<ULT> ultList,int quantum) {
        super(processID, kltID, ultList);
        readyQueue=new LinkedBlockingQueue<>(ultList);
        this.quantum=quantum;
    }



    public NodeIO run() throws NotRunneableThread, InvalidArgumentException {
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
        }else if(quantum==usedQuantum){
            usedQuantum=0;
            readyQueue.remove();
            readyQueue.add(toRunNow);
        }

        if(readyQueue.size()==0){
            state= Enum.ThreadState.FINISHED;
        }
        return new NodeIO(this,toRunNow.ultID,resp.typeJob,resp.amount);
    }
}
