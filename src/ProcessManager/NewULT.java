package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Response.ResponseULT;

import java.util.*;

public class NewULT {
    private ArrayList<Task> taskList;
    protected int runningTask;

    public NewULT(ArrayList<Task> taskList) {
        this.taskList = taskList;
        this.runningTask = 0;
    }
    public boolean isFinished(){
        if(runningTask>=taskList.size()){
            return true;
        }
        return false;
    }

    public ResponseULT run() throws NotRunneableThread {
        if(this.isFinished()){
            throw new NotRunneableThread("Cant run a finished ULT");
        }
        Task taskToRun;
        try{
            taskToRun=taskList.get(runningTask);
        }catch(IndexOutOfBoundsException e){
            runningTask=-1;
            return new ResponseULT(0);
        }
        if(taskToRun.job!= Enum.Job.CPU){
            int amountToSend=taskToRun.amount;
            taskToRun.amount=0;
            runningTask++;
            return new ResponseULT(taskToRun.job,amountToSend,0);
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
                    }
                }
            }
            return new ResponseULT(1);
        }
    }
}
