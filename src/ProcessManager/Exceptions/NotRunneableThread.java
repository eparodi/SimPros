package ProcessManager.Exceptions;

/**
 * Created by Vaati on 05-May-17.
 */
public class NotRunneableThread extends Exception {
    public NotRunneableThread() {
        super("The thread is NOT ready.");
    }
    public NotRunneableThread(String s){
        super(s);
    }
}
