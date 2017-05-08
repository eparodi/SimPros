package ProcessManager;

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

    public void run(){
        for(int i=0;i<100;i++){ //TODO: Arreglar esta magia negra
            for(KLT k: kltList){ //mete en cola de listos los klt nuevos
                if(k.respawn==i){
                    readyQueue.add(k);
                }
            }
            for(Queue<NodeIO> q: ioList){ //consume la lista de los IOs
                NodeIO io=q.peek();
                if(io==null){
                    continue;
                }
                if(io.amount==0){
                    readyQueue.add(io.klt);
                    io.klt.state= Enum.ThreadState.READY; //seguro de vida, para que no se prenda fuego
                    q.remove();
                }else{
                    io.amount=io.amount-1;
                }
            }
            //BEGIN: proceso
            for(int core=0;core<processorUnits;core++){
                KLT toExecute=readyQueue.peek();
                if(toExecute==null){
                    continue;
                }
                //TODO: continuar esto

            }
            //END: proceso

        }

    }

}
