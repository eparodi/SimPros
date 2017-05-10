package ProcessManager.Output;


import sun.awt.image.ImageWatched;
import sun.plugin.dom.core.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    public Iteration(LinkedList<LinkedList<Integer>> ioIntList, ArrayList<ExecutionDetail> proc){
        this.io1=ioIntList.get(0);
        this.io2=ioIntList.get(1);
        this.io3=ioIntList.get(2);
        this.exDetail =proc;
    }

    @Override
    public String toString(){
         return exDetail.toString()+" IO1:"+io1.toString()+" IO2:"+io2.toString()+" IO3:"+io3.toString();
    }
}
