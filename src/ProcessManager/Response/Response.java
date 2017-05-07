package ProcessManager.Response;

import ProcessManager.Enum;

public class Response {
    protected Enum.Job typeJob;
    protected int amount;
    protected int processed;

    public Response(int processed){
        this.typeJob= Enum.Job.CPU;
        this.amount=0;
        this.processed=0;
    }

    public Response(Enum.Job typeJob, int amount, int processed){
        this.typeJob=typeJob;
        this.amount=amount;
        this.processed=processed;
    }
}
