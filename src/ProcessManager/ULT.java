package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Response.Response;

import java.util.*;

public class ULT {
    private ArrayList<Task> taskList;
    protected int runningTask;
    protected int waitingTime;
    protected int kltID;
    public int ultID;

    public ULT(int kltID,int ultID,ArrayList<Task> taskList) {
        this.kltID=kltID;
        this.ultID=ultID;
        this.taskList = taskList;
        this.runningTask = 0;
        this.waitingTime = 0;
    }

    public boolean isFinished(){
        if(runningTask>=taskList.size()){
            return true;
        }
        return false;
    }

    public double priority(){
        return 1+(waitingTime/remainingRunTime());
    }

    public int remainingRunTime(){ //aa
        int acumulator=0;
        for(Task t: taskList){ //TODO fijarse si van los devices, segun ELISEO si van los devices
            if(t.job==Enum.Job.CPU) {
                acumulator = t.amount;
            }
        }
        return acumulator;
    }

    public void waiting(){
        this.waitingTime++;
    }

    public Response run() throws NotRunneableThread {
        Task taskToRun;
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run a finished ULT");
        }
        taskToRun=taskList.get(runningTask);
        waitingTime=0;
        if(taskToRun.job!= Enum.Job.CPU){
            int amountToSend=taskToRun.amount;
            taskToRun.amount=0;
            runningTask++;
            return new Response(kltID,ultID,taskToRun.job,amountToSend,0); //Este caso nunca deberia pasar;
        }else{
            taskToRun.amount=taskToRun.amount-1;
            if(taskToRun.amount==0){
                runningTask++;
                if(!this.isFinished()){
                    taskToRun=taskList.get(runningTask);
                    if(taskToRun.job!= Enum.Job.CPU){
                        int amountToSend = taskToRun.amount;
                        taskToRun.amount = 0;
                        runningTask++;
                        return new Response(kltID,ultID,taskToRun.job,amountToSend,1);
                    }
                }
            }
            return new Response(kltID,ultID,1);
        }
    }
}
