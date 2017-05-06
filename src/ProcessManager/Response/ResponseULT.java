package ProcessManager.Response;

import ProcessManager.Enum;

public class ResponseULT {
    protected Enum.Job typeJob;
    protected int amount;
    protected int processed;

    public ResponseULT(int processed){
        this.typeJob= Enum.Job.CPU;
        this.amount=0;
        this.processed=0;
    }

    public ResponseULT(Enum.Job typeJob,int amount,int processed){
        this.typeJob=typeJob;
        this.amount=amount;
        this.processed=processed;
    }
}
