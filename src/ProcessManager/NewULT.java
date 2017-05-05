package ProcessManager;

import java.util.*;

public class NewULT {
    private ArrayList<Task> taskList;
    protected int running_task; // -1: si termino, sino da el indice con el que ta trabajando
    private Enum.ThreadState state;

    public NewULT(ArrayList<Task> taskList) {
        this.taskList = taskList;
        this.running_task = 0;
    }

    public boolean isFinished(){
        if(running_task==-1){
            return true;
        }
        return false;
    }
}
