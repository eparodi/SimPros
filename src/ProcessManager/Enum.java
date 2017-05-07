package ProcessManager;


public class Enum {
    public static enum Job{
        CPU, IO1, IO2, IO3
    }
    public static enum ThreadState {
        READY, BLOCKED, FINISHED
    }
    @Deprecated
    public static enum Algorithm {
        FIFO, RR, HRRN, SPN, SRT
    }
    /*
    * SPN SomePreciousNullObject WHO KNOWS
    * SRT SHORTER REMAINING TIME
    * HRRN highest response ratio next
    * RR robin
    * FIFO trivial
    * */
}
