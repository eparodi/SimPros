package Interpreter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tp2.*;
import tp2.Process;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;

public class Interpreter {

    private static Integer rows;
    private static Integer coreNumber;
    private static HashMap<Integer, Integer> truePositionsMap;
    private static String[][] infoMatrix;

    public static void main(String[] args){
        for (int i = 1 ; i <= 1; i++){
            createRandomJSON("JSONExamples/example-"+i+".json", true);
            System.out.println("JSONExamples/example-"+i+".json");
            long currentTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - currentTime < 1000)
                ;
            try {
                Scheduler scheduler = jsonToProcess("JSONExamples/example-"+i+".json");
                TraceManager tManager = new TraceManager();
                scheduler.run(tManager);
                tManager.setGantt(rows, coreNumber, truePositionsMap);
                tManager.printGantt(infoMatrix);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static Scheduler jsonToProcess(String pathname) throws FileNotFoundException {
        // Read JSON data.
        BufferedReader reader = new BufferedReader(new FileReader(pathname));
        String json = "";
        for (Object s: reader.lines().toArray()){
            json += s + "\n";
        }

        JSONObject data = new JSONObject(json);

        // Positions
        truePositionsMap = new HashMap<>();
        Integer index = 0;
        // Threads Information
        infoMatrix = new String[rows][3];

        // IO Devices
        Integer ioDevices = data.getInt("io_devices");
        // Cores
        coreNumber = data.getInt("cores");
        ArrayList<Core> cores = new ArrayList<>();
        for (int i = 0 ; i < coreNumber ; i++) {
            cores.add(new Core(i + 1));
        }

        // Process
        JSONArray processes = data.getJSONArray("process");
        List<tp2.Process> processList = new ArrayList<>();
        for (Object processValue : processes){
            infoMatrix[index][0] = "P";
            JSONObject process = (JSONObject) processValue;
            Integer id = process.getInt("id");
            Integer arrivalTime = process.getInt("arrival_time");
            JSONArray klts = process.getJSONArray("klt");
            List<KernelLevelThread> kltList = new ArrayList<>();
            for (Object kltValue: klts){
                String s = "K_";
                JSONObject klt = (JSONObject) kltValue;
                Integer kltId = klt.getInt("id");
                if (klt.getBoolean("ult")){
                    Integer algorithm = klt.getInt("algorithm");
                    switch (algorithm) {
                        case 0:
                            s += "FIFO";
                            break;
                        case 1:
                            s += "RR_Q";
                            break;
                        case 2:
                            s += "SPN_";
                            break;
                        case 3:
                            s += "SRT_";
                            break;
                        default:
                            s += "HRRN";
                    }
                    infoMatrix[index][1] = s;
                    JSONArray ults = klt.getJSONArray("ults");
                    List<UserLevelThread> ultList = new ArrayList<>();
                    for (Object ultValue: ults){
                        infoMatrix[index][2] = "U";
                        JSONObject ult = (JSONObject) ultValue;
                        Integer arrivalTimeULT = ult.getInt("arrival_time");
                        Integer ultId = ult.getInt("id");
                        truePositionsMap.put(ultId, index);
                        index ++;
                        List<Task> tasks = getTasks(ult.getJSONArray("tasks"));
                        ultList.add(new UserLevelThread((ArrayList<Task>) tasks,ultId,arrivalTimeULT));
                    }
                    kltList.add(new KernelLevelThread((ArrayList<UserLevelThread>) ultList,algorithm,kltId,id));
                }else{
                    s += "____";
                    infoMatrix[index][1] = s;
                    truePositionsMap.put(kltId, index);
                    index ++;
                    List<Task> tasks = getTasks(klt.getJSONArray("tasks"));
                    kltList.add(new KernelLevelThread((ArrayList<Task>) tasks,kltId,id));
                }

            }
            processList.add(new tp2.Process((ArrayList<KernelLevelThread>) kltList,id,arrivalTime));
        }
        return new Scheduler(cores, (ArrayList<Process>) processList,ioDevices);
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

    public static void createRandomJSON(String filename, boolean limited){
        JSONObject data = new JSONObject();
        Random r = new Random();

        Integer kltID = 100;
        Integer ultID = 1000;

        rows = 0;

        // Devices
        Integer ioDevices;
        if (limited)
            ioDevices = r.nextInt(3) + 1;
        else
            ioDevices = r.nextInt(5) + 2;
        data.put("io_devices",ioDevices);

        // Cores
        Integer cores;
        if (limited)
            cores = r.nextInt(2) + 1;
        else
            cores = r.nextInt(5) + 1;
        data.put("cores",cores);

        // Processes
        JSONArray processes = new JSONArray();
        Integer processNumber = r.nextInt(10) + 1;
        Integer arrivalTime = 0;

        for (int i = 0 ; i < processNumber; i++){
            Integer id = i + 1;
            JSONObject process = new JSONObject();
            process.put("id",id);
            process.put("arrival_time",arrivalTime);
            arrivalTime += r.nextInt(3);

            // Klts
            Integer kltNumber;
            if (limited)
                kltNumber = r.nextInt(2) + 1;
            else
                kltNumber = r.nextInt(5) + 1;
            JSONArray klts = new JSONArray();
            for (int j = 0 ; j < kltNumber ; j++ ){
                rows ++;
                JSONObject klt = new JSONObject();
                klt.put("id", kltID++);
                if(r.nextBoolean()){
                    rows --;
                    // Ults
                    klt.put("ult",true);
                    Integer algorithm = r.nextInt(5);
                    if (algorithm == 1)
                        algorithm --;
                    klt.put("algorithm",algorithm);
                    Integer ultNumber;
                    if (limited)
                        ultNumber = r.nextInt(1) + 2;
                    else
                        ultNumber = r.nextInt(4) + 2;
                    JSONArray ults = new JSONArray();
                    Integer ultArrivalTime = 0;
                    for (int k = 0 ; k < ultNumber ; k++ ){
                        rows ++;
                        JSONObject ult = new JSONObject();
                        ult.put("arrival_time",ultArrivalTime);
                        ultArrivalTime += r.nextInt(3);
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
            Integer quantum = r.nextInt(5) + 1;
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
