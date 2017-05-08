package ProcessManager;

import ProcessManager.Exceptions.NotRunneableThread;
import ProcessManager.ThreadMode.ThreadFIFO;
import ProcessManager.ThreadMode.*;
import ProcessManager.ThreadMode.ThreadRR;
import com.sun.javaws.exceptions.InvalidArgumentException;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) throws NotRunneableThread, InvalidArgumentException {
        ArrayList<Task> alfa=new ArrayList<>();
        alfa.add(new Task(30, Enum.Job.CPU));
        ULT primer=new ULT(1,1,alfa);
        ArrayList<ULT> ul=new ArrayList<>();
        ul.add(primer);
        KLT mother=new ThreadSRT(1,1,ul);
        ArrayList<KLT> kk=new ArrayList<>();
        kk.add(mother);
        Scheduler s=new Scheduler(0,kk,-1);
        s.run();
    }
}
