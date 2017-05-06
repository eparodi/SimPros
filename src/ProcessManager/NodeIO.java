package ProcessManager;


public class NodeIO {
    protected int processID;
    protected NewKLT klt;
    protected Enum.Job ioID;
    protected int amount;

    public NodeIO(int processID, NewKLT klt, Enum.Job ioID,int amount){
        //TODO: comprobar que no se crea uno roto
        this.processID=processID;
        this.klt=klt;
        this.ioID=ioID;
        this.amount=amount;
    }
}
