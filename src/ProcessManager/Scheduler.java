package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.Output.ExecutionDetail;
import ProcessManager.Output.Iteration;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class Scheduler {

    private int processIndex=1; //todo: para el addprocessor
    private int kltIndex=1;



    private int processorUnits=1; //1,2
    private int quantum=-1; //-1 cuando es FIFO
    private List<Integer> usedQuantum=new ArrayList<>(2);


    private List<KLT> kltList;
    private List<Iteration> ans;
    private List<Queue<NodeIO>> ioList;

    private List<Queue<KLT>> readyListQ;
    private Queue<KLT> waitingToDeploy; //


    public Scheduler(int processorUnits, List<KLT> kltList, int quantum){
        this.processorUnits=processorUnits;
        this.kltList=kltList;
        this.quantum=quantum;
        //this.readyQueue= new LinkedBlockingQueue<>();

        this.readyListQ= new ArrayList<>(2);
        this.readyListQ.add(new LinkedBlockingQueue<>());
        this.readyListQ.add(new LinkedBlockingQueue<>());

        this.waitingToDeploy=new LinkedBlockingQueue<>();

        this.usedQuantum.add(0);
        this.usedQuantum.add(0);

        ioList=new ArrayList<>(3);
        for(int i=0;i<3;i++){
            ioList.add(i,new LinkedBlockingQueue<>());
        }
    }

    public boolean isFinished(){
        for(KLT k: kltList){
            if(!k.isFinished()){
                return false;
            }
        }
        return true;
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
                readyListQ.get(io.klt.inCore).add(io.klt);
                io.klt.state = Enum.ThreadState.READY;
                q.remove();
            } else {
                io.consume();
            }
        }
    }
    private int bestCore(){
        int firstC=0,secondC=0;
        for (KLT k : kltList){
            if(k.inCore==0){
                firstC++;
            }
            if(k.inCore==1){
                secondC++;
            }
        }
        if(firstC<=secondC){
            return 0;
        }else{
            return 1;
        }
    }

    private void addIncomingKLT(int i){
        for (KLT k : kltList) { //mete en cola de listos los klt nuevos
            if (k.respawn == i) {
                if(processorUnits==1){
                    readyListQ.get(0).add(k);
                    k.inCore=0;
                }else{
                    int selected=bestCore();
                    readyListQ.get(selected).add(k);
                    k.inCore=selected;
                }
            }
        }
    }

    private void putInIoQueue(NodeIO node){
        if (node.ioID == Enum.Job.IO1) {
            ioList.get(0).add(node);
        } else if (node.ioID == Enum.Job.IO2) {
            ioList.get(1).add(node);
        } else if (node.ioID == Enum.Job.IO3) {
            ioList.get(2).add(node);
        }
    }


    private ExecutionDetail runKLT(KLT toExe) throws NotRunneableThread, InvalidArgumentException {
        if (toExe == null) {
            return new ExecutionDetail();
        }else{
            NodeIO node = toExe.run();
            if(node.ioID != Enum.Job.CPU){
                putInIoQueue(node);
                //readyQueue.remove();
                readyListQ.get(node.klt.inCore).remove();
            }
            if(toExe.isFinished()){
                //readyQueue.remove();
                readyListQ.get(node.klt.inCore).remove();
            }
            return new ExecutionDetail(node.klt.processID,node.klt.kltID,node.ultID);
        }
    }

    private void biyiarada(int i){
        for (KLT k : kltList) { //mete en cola de listos los klt nuevos
            if (k.respawn == i) {
                waitingToDeploy.add(k);
            }
        }
    }


    public List<Iteration> execute() throws NotRunneableThread, InvalidArgumentException {
        ans = new LinkedList<>();
        int i=0;
        usedQuantum.set(0,0);
        usedQuantum.set(1,0);


        while(!this.isFinished()) {
            //addIncomingKLT(i);
            biyiarada(i);
            consumeIO();
            ArrayList<ExecutionDetail> exeList=new ArrayList<>(2);
            Iteration iter=new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList);

            KLT toEx = readyListQ.get(0).peek();
            ExecutionDetail ex=runKLT(toEx);
            if(ex.processID==0){  //BIYI
                toEx=waitingToDeploy.poll();
                if(toEx!=null){
                    toEx.inCore=0;
                    readyListQ.get(0).add(toEx);
                    ex=runKLT(toEx);
                }
            }
            exeList.add(ex);
            if(processorUnits==2){
                toEx = readyListQ.get(1).peek();
                ex=runKLT(toEx);
                if(ex.processID==0){ //BIYI
                    toEx=waitingToDeploy.poll();
                    if(toEx!=null){
                        toEx.inCore=1;
                        readyListQ.get(1).add(toEx);
                        ex=runKLT(toEx);
                    }
                }
                exeList.add(ex);
            }
            //iter=new Iteration(lkQe(ioList.get(0)), lkQe(ioList.get(1)),lkQe(ioList.get(2)),exeList);//TODO SACAR
            iter.setExDetail(exeList);
            System.out.println(iter);
            ans.add(iter);
            i++;
        }
        return ans;
    }


}
