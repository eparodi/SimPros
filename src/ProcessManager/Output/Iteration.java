package ProcessManager.Output;


import java.util.ArrayList;
import java.util.LinkedList;

public class Iteration {
    LinkedList<Integer> io1;
    LinkedList<Integer> io2;
    LinkedList<Integer> io3;
    ArrayList<ExecutionDetail> exDetail;

    public Iteration(LinkedList<Integer> io1, LinkedList<Integer> io2, LinkedList<Integer> io3, ArrayList<ExecutionDetail> proc){
        this.io1=io1;
        this.io2=io2;
        this.io3=io3;
        this.exDetail =proc;
    }
}
