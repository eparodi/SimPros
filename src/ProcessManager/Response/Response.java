package ProcessManager.Response;

import ProcessManager.Enum;

public class Response {
    public int ultID;
    public int kltID;
    public Enum.Job typeJob;
    public int amount;
    public int processed;

    public Response(int kltID,int ultID, int processed){
        this.kltID=kltID;
        this.ultID=ultID;
        this.typeJob= Enum.Job.CPU;
        this.amount=0;
        this.processed=0;
    }

    public Response(int kltID,int ultID, Enum.Job typeJob, int amount, int processed){
        this.kltID=kltID;
        this.ultID=ultID;
        this.typeJob=typeJob;
        this.amount=amount;
        this.processed=processed;
    }
}
