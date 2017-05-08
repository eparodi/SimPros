package ProcessManager.Output;

public class ExecutionDetail {
    int kltID;
    int ultID;
    public ExecutionDetail(int kltID, int ultID){
        this.kltID=kltID;
        this.ultID=ultID;
    }
    public String toString(){
        return "Ex:"+String.valueOf(kltID)+","+String.valueOf(ultID);
    }
}
