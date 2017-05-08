package ProcessManager;


import com.sun.javaws.exceptions.InvalidArgumentException;

public class NodeIO {
    protected KLT klt;
    protected int ultID;
    protected Enum.Job ioID;
    protected int amount;

    public NodeIO(KLT klt,int ultID, Enum.Job ioID, int amount) throws InvalidArgumentException{
        this.klt=klt;
        this.ultID=ultID;
        this.ioID=ioID;
        this.amount=amount;
    }
}
