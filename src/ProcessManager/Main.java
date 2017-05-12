package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.ThreadMode.ThreadFIFO;
import ProcessManager.ThreadMode.*;
import ProcessManager.ThreadMode.ThreadRR;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) throws NotRunneableThread, InvalidArgumentException {
        ArrayList<Task> alfa=new ArrayList<>();
        alfa.add(new Task(5, Enum.Job.CPU));
        alfa.add(new Task(5, Enum.Job.IO1));
        alfa.add(new Task(3, Enum.Job.CPU));
        ULT primer=new ULT(1,11,alfa);
        ArrayList<ULT> ul=new ArrayList<>();
        ul.add(primer);
        alfa=new ArrayList<>();
        alfa.add(new Task(5, Enum.Job.CPU));
        alfa.add(new Task(1, Enum.Job.IO3));
        alfa.add(new Task(1, Enum.Job.CPU));
        primer=new ULT(1,22,alfa);
        ul.add(primer);
        alfa=new ArrayList<>();
        alfa.add(new Task(10, Enum.Job.CPU));
        alfa.add(new Task(1, Enum.Job.IO2));
        alfa.add(new Task(2, Enum.Job.CPU));
        primer=new ULT(1,33,alfa);
        ul.add(primer);
        KLT mother=new ThreadRR(1,1,ul,3);
        mother.respawn=10;
        ArrayList<KLT> kk=new ArrayList<>();
        kk.add(mother);
        Scheduler s=new Scheduler(2,kk,-1);
        s.execute();
    }
}
