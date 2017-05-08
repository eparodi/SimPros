package ProcessManager.Output;


import sun.awt.image.ImageWatched;

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
    @Override
    public String toString(){
        //ret.concat();
        //ret.concat(io2.toString());
        //ret.concat(io3.toString());
        //ret.concat(exDetail.toString());
        return exDetail.toString()+" IO:"+io1.toString()+" "+io2.toString()+" "+io3.toString();
    }
}
