package ProcessManager;


import com.sun.javaws.exceptions.InvalidArgumentException;

public class NodeIO {
    protected int processID;
    protected KLT klt;
    protected Enum.Job ioID;
    protected int amount;

    public NodeIO(int processID, KLT klt, Enum.Job ioID, int amount) throws InvalidArgumentException{
        if(amount<=0 || processID<=0 || klt==null) {
            throw new InvalidArgumentException(new String[]{"Invalid NodeIO"});
        }
        this.processID=processID;
        this.klt=klt;
        this.ioID=ioID;
        this.amount=amount;
    }
}
