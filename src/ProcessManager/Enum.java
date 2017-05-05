package ProcessManager;


public class Enum {
    public static enum Type {
        CPU, IO
    }
    public static enum Device {
        IO1, IO2, IO3
    }
    public static enum Job{
        CPU, IO1, IO2, IO3
    }
    public static enum ThreadState {
        NONBLOCKED, BLOCKED, FINISHED
    }

    public static enum Algorithm {
        FIFO, RR, HRRN, SPN, SRT
    }
}
