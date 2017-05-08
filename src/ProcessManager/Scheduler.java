package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Output.ExecutionDetail;
import ProcessManager.Output.Iteration;
import ProcessManager.Response.Response;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler {
    int processorUnits=1; //1,2
    int quantum=-1; //-1 cuando es FIFO

    List<KLT> kltList;
    List<Queue<NodeIO>> ioList;
    Queue<KLT> readyQueue;




    public Scheduler(int processorUnits, List<KLT> kltList, int quantum){
        this.processorUnits=processorUnits;
        this.kltList=kltList;
        this.quantum=quantum;
        ioList=new ArrayList<>(3);
        for(int i=0;i<3;i++){
            ioList.add(i, new LinkedBlockingQueue<>());
        }
    }

    private LinkedList<Integer> lkQe(Queue<NodeIO> ioQueue){ //TODO verlo despues
        LinkedList<Integer> ans=new LinkedList<>();
        for(NodeIO n: ioQueue){
            ans.add(n.klt.kltID);
        }
        return ans;
    }

    public List<Iteration> run() throws NotRunneableThread, InvalidArgumentException {
        List<Iteration> ans = new LinkedList<>();
        for (int i = 0; i < 100; i++) { //TODO: Arreglar esta magia negra
            for (KLT k : kltList) { //mete en cola de listos los klt nuevos
                if (k.respawn == i) {
                    readyQueue.add(k);
                }
            }
            for (Queue<NodeIO> q : ioList) { //consume la lista de los IOs
                NodeIO io = q.peek();
                if (io == null) {
                    continue;
                }
                if (io.amount == 0) {
                    readyQueue.add(io.klt);
                    io.klt.state = Enum.ThreadState.READY;
                    q.remove();
                } else {
                    io.amount = io.amount - 1;
                }
            }
            //BEGIN: proceso
            //for(int core=0;core<processorUnits;core++){ //TODO arreglar para varios procesadores
            KLT toExecute = readyQueue.peek(); //TODO IMP
            //ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(0)), lkQe(ioList.get(0)),new ArrayList<>(1)));
            if (toExecute == null) {
                break;
            }
            else {
                NodeIO node;
                node = toExecute.run();
                if(node.ioID != Enum.Job.CPU){
                    if (node.ioID == Enum.Job.IO1) {
                        ioList.get(0).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
                    } else if (node.ioID == Enum.Job.IO2) {
                        ioList.get(1).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
                    } else if (node.ioID == Enum.Job.IO3) {
                        ioList.get(2).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
                    }
                    KLT cache=readyQueue.remove();
                    readyQueue.add(cache);
                }
                ArrayList<ExecutionDetail> exeList=new ArrayList<>(1);
                exeList.add(new ExecutionDetail(node.klt.kltID,node.ultID));
                ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(0)),lkQe(ioList.get(0)),exeList));
            }

            //}
            //END: proceso
        }
        return ans; //TODO REMOVE
    }


}
