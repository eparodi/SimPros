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

public class ThreadFIFO extends KLT {
    private Queue<ULT>  readyQueue;

    public ThreadFIFO(int processID,int kltID,ArrayList<ULT> ultList) {
        super(processID,kltID,ultList);
        readyQueue=new LinkedBlockingQueue<>(ultList);
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
        if(toRunNow==null){
            throw new NotRunneableThread("ReadyQueue is empty");
        }
        Response resp=toRunNow.run();
        if(resp.typeJob!= Enum.Job.CPU){
            state= Enum.ThreadState.BLOCKED;
//            readyQueue.remove();
//            readyQueue.add(toRunNow);
        }
        if(toRunNow.isFinished()){
            readyQueue.remove();
        }
        return new NodeIO(this,toRunNow.ultID,resp.typeJob,resp.amount);
    }

}
