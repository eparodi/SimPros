package ProcessManager.Output;

public class ExecutionDetail {

    //todo FALTA EL IDENTIFICADOR DEL PROCESO
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
