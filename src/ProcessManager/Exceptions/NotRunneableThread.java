package ProcessManager.Exceptions;

public class NotRunneableThread extends Exception {
    public NotRunneableThread() {
        super("The thread is NOT ready.");
    }
    public NotRunneableThread(String s){
        super(s);
    }
}
