package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Output.ExecutionDetail;
import ProcessManager.Output.Iteration;
import ProcessManager.Response.Response;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//todo: imeplementar addprocess, dejamos a lo Linux los KLT manda.
//todo:
public class Scheduler {
    private int processIndex=1;

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
                if(isFinished()){
                    break;
                }
                ArrayList<ExecutionDetail> exeList=new ArrayList<>(1);
                exeList.add(new ExecutionDetail(0,0));
                System.out.println(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList)); //TODO SACAR
                ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList));
                continue;
            }
            else {
                if(toExecute.state!= Enum.ThreadState.READY){
                    ArrayList<ExecutionDetail> exeList=new ArrayList<>(1);
                    exeList.add(new ExecutionDetail(0,0));
                    System.out.println(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList)); //TODO SACAR
                    ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList));
                    continue;
                }
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
                    readyQueue.remove();
                }else{
                    if(toExecute.isFinished()){
                        readyQueue.remove();
                    }
                }
                ArrayList<ExecutionDetail> exeList=new ArrayList<>(1);
                exeList.add(new ExecutionDetail(node.klt.kltID,node.ultID));
                System.out.println(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList)); //TODO SACAR
                ans.add(new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList));
            }

            //}
            //END: proceso
        }
        return ans; //TODO REMOVE
    }


}
