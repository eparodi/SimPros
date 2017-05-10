package ProcessManager.Output;

public class ExecutionDetail {

    private int processID;
    private int kltID;
    private int ultID;

    @Deprecated
    public ExecutionDetail(int kltID, int ultID){ //TODO ELIMINAR
        this.processID=-9;
        this.kltID=kltID;
        this.ultID=ultID;
    }

    public ExecutionDetail(int processID,int kltID, int ultID){
        this.processID=processID;
        this.kltID=kltID;
        this.ultID=ultID;
    }

    public ExecutionDetail(){ //caso que no se ejecuto nada
        this.processID=0;
        this.kltID=0;
        this.ultID=0;
    }

    public String toString(){
        return "Ex:"+String.valueOf(processID)+","+String.valueOf(kltID)+","+String.valueOf(ultID);
    }
}
