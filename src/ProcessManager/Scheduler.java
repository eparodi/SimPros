package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Output.ExecutionDetail;
import ProcessManager.Output.Iteration;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

//todo: imeplementar addprocess, dejamos a lo Linux los KLT manda.
//todo:
public class Scheduler {
    private int processIndex=1;
    List<Iteration> ans;

    private int processorUnits=1; //1,2
    private int quantum=-1; //-1 cuando es FIFO

    private List<KLT> kltList;
    private List<Queue<NodeIO>> ioList;
    private Queue<KLT> readyQueue;

    public boolean isFinished(){
        for(KLT k: kltList){
            if(!k.isFinished()){
                return false;
            }
        }
        return true;
    }

    public Scheduler(int processorUnits, List<KLT> kltList, int quantum){
        this.processorUnits=processorUnits;
        this.kltList=kltList;
        this.quantum=quantum;
        this.readyQueue= new LinkedBlockingQueue<KLT>();

        ioList=new ArrayList<>(3);
        for(int i=0;i<3;i++){
            ioList.add(i,new LinkedBlockingQueue<>());
        }
    }

    private LinkedList<Integer> lkQe(Queue<NodeIO> ioQueue){ //TODO verlo despues
        LinkedList<Integer> ans=new LinkedList<>();
        for(NodeIO n: ioQueue){
            ans.add(n.klt.kltID);
        }
        return ans;
    }

    private void consumeIO(){
        for (Queue<NodeIO> q : ioList) { //consume la lista de los IOs
            NodeIO io = q.peek();
            if (io == null) {
                continue;
            }
            if (io.isFinished()){
                readyQueue.add(io.klt);
                io.klt.state = Enum.ThreadState.READY;
                q.remove();
            } else {
                io.consume();
            }
        }
    }
    private void addIncomingKLT(int i){ //TODO IMPLEMEMNTAR PARA MULTICORE CHE
        for (KLT k : kltList) { //mete en cola de listos los klt nuevos
            if (k.respawn == i) {
                readyQueue.add(k);
            }
        }
    }

    private void putInIoQueue(NodeIO node){
        if (node.ioID == Enum.Job.IO1) {
            ioList.get(0).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
        } else if (node.ioID == Enum.Job.IO2) {
            ioList.get(1).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
        } else if (node.ioID == Enum.Job.IO3) {
            ioList.get(2).add(new NodeIO(node.klt,node.ultID,Enum.Job.IO1,node.amount));
        }
    }

    private void generateExDetail(ExecutionDetail ex){
        ArrayList<ExecutionDetail> exeList=new ArrayList<>(1);
        exeList.add(ex);
        System.out.println(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList)); //TODO SACAR
        ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList));
    }


    public List<Iteration> run() throws NotRunneableThread, InvalidArgumentException {
        ans = new LinkedList<>();
        int i=0;

        while(!this.isFinished()) {

            addIncomingKLT(i);
            consumeIO();

            KLT toExecute = readyQueue.peek();
            if (toExecute == null) {
                if(isFinished()){
                    break;
                }
                generateExDetail(new ExecutionDetail());
            }else{
                NodeIO node = toExecute.run();
                if(node.ioID != Enum.Job.CPU){
                    putInIoQueue(node);
                    readyQueue.remove();
                }
                if(toExecute.isFinished()){
                    readyQueue.remove();
                }
                generateExDetail(new ExecutionDetail(node.klt.processID,node.klt.kltID,node.ultID));
            }
            i++;
        }
        return ans;
    }


}
