package ProcessManager;

import java.util.ArrayList;
import java.util.List;

public class NewProcess {
    private int processID;
    private List<KernelLevelThread> kernelThreadsList;

    public NewProcess(List<KernelLevelThread> kernelThreadsList){
        this.kernelThreadsList= kernelThreadsList;
    }

}
