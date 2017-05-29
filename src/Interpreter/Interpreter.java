package Interpreter;

import org.json.JSONArray;
import org.json.JSONObject;
import tp2.*;
import tp2.Process;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Interpreter {

    public static void main(String[] args){
        for (int i = 1 ; i <= 1; i++){
            createRandomJSON("JSONExamples/example-"+i+".json");
            System.out.println("JSONExamples/example-"+i+".json");
            try {
                Scheduler scheduler = jsonToProcess("JSONExamples/example-"+i+".json", null);
                TraceManager tManager = new TraceManager();
                scheduler.run(tManager);
                tManager.setInfo(scheduler.getInfoMatrix());
                tManager.setGantt(scheduler.getInfoMatrix().size(), scheduler.getCores(), scheduler.getPositionsMap());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static Scheduler jsonToProcess(String pathname, Integer quantumNumber) throws FileNotFoundException {
        // Read JSON data.
        BufferedReader reader = new BufferedReader(new FileReader(pathname));
        String json = "";
        for (Object s: reader.lines().toArray()){
            json += s + "\n";
        }

        JSONObject data = new JSONObject(json);
        // IO Devices
        Integer ioDevices = data.getInt("io_devices");
        // Rows
        Integer rows = 0;
        // Cores
        Integer coreNumber = data.getInt("cores");
        ArrayList<Core> cores = new ArrayList<>();
        for (int i = 0 ; i < coreNumber ; i++){
            cores.add(new Core(i + 1));
        }
        // Useful Gantt Info
        ArrayList<String[]> infoMatrix = new ArrayList<>();
        HashMap<Integer, Integer> positionsMap = new HashMap<>();

        // Process
        JSONArray processes = data.getJSONArray("process");
        List<tp2.Process> processList = new ArrayList<>();
        for (Object processValue : processes){
            String[] s = new String[3];
            s[0] = "P";
            infoMatrix.add(s);
            JSONObject process = (JSONObject) processValue;
            Integer id = process.getInt("id");
            Integer arrivalTime = process.getInt("arrival_time");
            JSONArray klts = process.getJSONArray("klt");
            List<KernelLevelThread> kltList = new ArrayList<>();
            for (Object kltValue: klts){
                rows ++;
                JSONObject klt = (JSONObject) kltValue;
                Integer kltId = klt.getInt("id");
                if (klt.getBoolean("ult")){
                    rows --;
                    positionsMap.put(kltId,rows);
                    Integer algorithm = klt.getInt("algorithm");
                    String info = "K ";
                    switch (algorithm) {
                        case 0:
                            info += "FIFO";
                            break;
                        case 1:
                            Integer quantum = klt.getInt("quantum");
                            if (quantum < 10)
                                info += "RR " + quantum;
                            else
                                info += "RR" + quantum;
                            break;
                        case 2:
                            info += "SPN ";
                            break;
                        case 3:
                            info += "SRT ";
                            break;
                        default:
                            info += "HRRN";
                            break;
                    }
                    if (infoMatrix.size() <= rows)
                        infoMatrix.add(new String[3]);
                    infoMatrix.get(rows)[1] = info;
                    JSONArray ults = klt.getJSONArray("ults");
                    List<UserLevelThread> ultList = new ArrayList<>();
                    for (Object ultValue: ults){
                        if (infoMatrix.size() <= rows)
                            infoMatrix.add(new String[3]);
                        infoMatrix.get(rows)[2] = "U";
                        rows ++;
                        JSONObject ult = (JSONObject) ultValue;
                        Integer arrivalTimeULT = ult.getInt("arrival_time");
                        Integer ultId = ult.getInt("id");
                        positionsMap.put(ultId, rows - 1);
                        List<Task> tasks = getTasks(ult.getJSONArray("tasks"));
                        ultList.add(new UserLevelThread((ArrayList<Task>) tasks,ultId,arrivalTimeULT));
                    }
                    kltList.add(new KernelLevelThread((ArrayList<UserLevelThread>) ultList,algorithm,kltId,id));
                }else{
                    positionsMap.put(kltId,rows);
                    if (infoMatrix.size() <= rows)
                        infoMatrix.add(new String[3]);
                    infoMatrix.get(rows - 1)[1] = "K     ";
                    List<Task> tasks = getTasks(klt.getJSONArray("tasks"));
                    kltList.add(new KernelLevelThread((ArrayList<Task>) tasks,kltId,id));
                }
            }
            processList.add(new tp2.Process((ArrayList<KernelLevelThread>) kltList,id,arrivalTime));
        }
        Scheduler scheduler;
        if ( quantumNumber == null){
            scheduler = new Scheduler(cores, (ArrayList<Process>) processList, ioDevices);
        }else{
            scheduler = new Scheduler(cores, (ArrayList<Process>) processList, ioDevices, quantumNumber);
        }
        scheduler.setInfo(infoMatrix, positionsMap);
        return scheduler;
    }

    private static List<Task> getTasks(JSONArray tasks){
        List<Task> taskList = new ArrayList<>();

        for (Object taskValue : tasks){
            JSONObject task = (JSONObject) taskValue;
            Integer quantum = task.getInt("quantum");
            Task taskObj;
            if (task.getBoolean("io")){
                taskObj = new Task(quantum,task.getInt("device_number"));
            }else{
                taskObj = new Task(quantum);
            }
            taskList.add(taskObj);
        }

        return taskList;
    }

    public static void createRandomJSON(String filename){
        JSONObject data = new JSONObject();
        Random r = new Random();

        Integer kltID = 100;
        Integer ultID = 1000;

        Integer ioDevices = r.nextInt(5) + 2;
        data.put("io_devices",ioDevices);
        data.put("cores",2);

        JSONArray processes = new JSONArray();
        int processNumber = r.nextInt(15);
        int arrivalTime = 0;

        for (int i = 0 ; i < processNumber; i++){
            Integer id = i + 1;
            JSONObject process = new JSONObject();
            process.put("id",id);
            process.put("arrival_time",arrivalTime);
            arrivalTime += r.nextInt(5);

            int kltNumber = r.nextInt(5) + 1;
            JSONArray klts = new JSONArray();
            for (int j = 0 ; j < kltNumber ; j++ ){
                JSONObject klt = new JSONObject();
                klt.put("id", kltID++);
                if(r.nextBoolean()){
                    klt.put("ult",true);
                    Integer algorithm = r.nextInt(5);
                    klt.put("algorithm",algorithm);
                    if (algorithm == 1) {
                        Integer quantum = r.nextInt(4) + 1;
                        klt.put("quantum", quantum);
                    }
                    Integer ultNumber = r.nextInt(4)+2;
                    JSONArray ults = new JSONArray();
                    int ultArrivalTime = 0;
                    for (int k = 0 ; k < ultNumber ; k++ ){
                        JSONObject ult = new JSONObject();
                        ult.put("arrival_time",ultArrivalTime);
                        ultArrivalTime += r.nextInt(5);
                        ult.put("id",ultID++);
                        ult.put("tasks",createTasks(ioDevices));
                        ults.put(ult);
                    }
                    klt.put("ults",ults);
                }else{
                    klt.put("ult",false);
                    klt.put("tasks",createTasks(ioDevices));
                }
                klts.put(klt);
            }
            process.put("klt", klts);
            processes.put(process);
        }
        data.put("process",processes);

        try{
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.print(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray createTasks(Integer ioDevices){
        JSONArray tasks = new JSONArray();
        Random r = new Random();
        boolean cpu = true;

        Integer taskNumber = r.nextInt(10) + 1;
        if (taskNumber % 2 == 0){
            taskNumber += 1;
        }
        for (int i = 0 ; i < taskNumber ; i++ ){
            JSONObject task = new JSONObject();
            Integer quantum = r.nextInt(10) + 1 ;
            task.put("quantum",quantum);
            if (cpu){
                cpu = false;
                task.put("io",false);
            }else{
                cpu = true;
                task.put("io",true);
                task.put("device_number",r.nextInt(ioDevices)+1);
            }
            tasks.put(task);
        }

        return tasks;
    }

}
