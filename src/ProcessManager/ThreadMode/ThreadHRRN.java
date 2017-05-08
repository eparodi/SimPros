package ProcessManager.ThreadMode;

import ProcessManager.Enum;
import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.KLT;
import ProcessManager.NodeIO;
import ProcessManager.Response.Response;
import ProcessManager.ULT;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;


public class ThreadHRRN extends KLT{


    public ThreadHRRN(int processID, int kltID, ArrayList<ULT> ultList) {
        super(processID, kltID, ultList);
    }


    public NodeIO run() throws NotRunneableThread, InvalidArgumentException {
        if(state!= Enum.ThreadState.READY){
            throw new NotRunneableThread();
        }
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run finished KLT");
        }
        ULT toRunNow=null;
        double prCandidate=1;
        for(ULT u: ultList){
            if(!u.isFinished()){
                if(u.priority()<=prCandidate){
                    toRunNow=u;
                    prCandidate=u.priority();
                }
            }
        }
        waiting(); //hacerlo DESPUES de elegir el thread a correr
        Response resp=toRunNow.run();
        if(resp.typeJob!= Enum.Job.CPU){
            state= Enum.ThreadState.BLOCKED;
        }
        return new NodeIO(this,toRunNow.ultID,resp.typeJob,resp.amount);
    }
}
