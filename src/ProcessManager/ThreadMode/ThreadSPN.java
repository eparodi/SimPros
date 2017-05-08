package ProcessManager.ThreadMode;


import ProcessManager.Enum;
import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.KLT;
import ProcessManager.Response.Response;
import ProcessManager.ULT;

import java.util.ArrayList;

public class ThreadSPN extends KLT{

    public ThreadSPN(int processID, ArrayList<ULT> ultList) {
        super(processID, ultList);
    }

    public Response run() throws NotRunneableThread {
        if(state!= Enum.ThreadState.READY){
            throw new NotRunneableThread();
        }
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run finished KLT");
        }
        ULT toRunNow=null;
        int remainingCandidate=1000;//TODO magic numberssss
        for(ULT u: ultList){
            if(!u.isFinished()){
                if(u.remainingRunTime()<remainingCandidate){
                    toRunNow=u;
                    remainingCandidate=u.remainingRunTime();
                }
            }
        }
        waiting(); //hacerlo DESPUES de elegir el thread a correr
        Response resp=toRunNow.run();
        if(resp.typeJob!= Enum.Job.CPU){
            state= Enum.ThreadState.BLOCKED;
        }
        return resp;
    }
}
