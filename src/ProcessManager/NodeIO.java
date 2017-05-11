package ProcessManager;


import com.sun.javaws.exceptions.InvalidArgumentException;

public class NodeIO {
    protected KLT klt;
    protected int ultID;
    protected Enum.Job ioID;
    private int amount;

    public NodeIO(KLT klt,int ultID, Enum.Job ioID, int amount){
        this.klt=klt;
        this.ultID=ultID;
        this.ioID=ioID;
        this.amount=amount;
    }

    public void consume(){
        if(amount>0){
            amount=amount-1;
        }
    }

    public boolean isFinished(){
        if(amount>0){
            return false;
        }
        return true;
    }
}
