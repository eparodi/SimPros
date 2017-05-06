package ProcessManager;


public class Enum {
    @Deprecated
    public static enum Type {
        CPU, IO
    }
    @Deprecated
    public static enum Device {
        IO1, IO2, IO3
    }
    public static enum Job{
        CPU, IO1, IO2, IO3
    }
    public static enum ThreadState {
        READY, NONBLOCKED, BLOCKED, FINISHED
    }
    public static enum Algorithm {
        FIFO, RR, HRRN, SPN, SRT
    }
}
