package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Response.Response;

import java.util.*;

public class ULT {
    private ArrayList<Task> taskList;
    protected int runningTask;
    protected int waitingTime;

    public ULT(ArrayList<Task> taskList) {
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

    public int remainingRunTime(){
        int acumulator=0;
        for(Task t: taskList){ //TODO fijarse si van los devices
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
        try{
            taskToRun=taskList.get(runningTask);
        }catch(IndexOutOfBoundsException e){
            runningTask=-1;
            return new Response(0);
        }
        waitingTime=0;
        if(taskToRun.job!= Enum.Job.CPU){
            int amountToSend=taskToRun.amount;
            taskToRun.amount=0;
            runningTask++;
            return new Response(taskToRun.job,amountToSend,0);
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
                        return new Response(taskToRun.job,amountToSend,1);
                    }
                }
            }
            return new Response(1);
        }
    }
}
