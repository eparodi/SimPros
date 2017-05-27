package Interpreter;

import org.json.JSONArray;
import org.json.JSONObject;
import tp2.*;
import tp2.Process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    public static Scheduler jsonToProcess(String pathname) throws FileNotFoundException {
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
        Integer rows = data.getInt("rows");
        // Cores
        Integer coreNumber = data.getInt("cores");
        ArrayList<Core> cores = new ArrayList<>();
        for (int i = 0 ; i < coreNumber ; i++){
            cores.add(new Core(i + 1));
        }

        // Process
        JSONArray processes = data.getJSONArray("process");
        List<tp2.Process> processList = new ArrayList<>();
        for (Object processValue : processes){
            JSONObject process = (JSONObject) processValue;
            Integer id = process.getInt("id");
            Integer arrivalTime = process.getInt("arrival_time");
            JSONArray klts = data.getJSONArray("klt");
            List<KernelLevelThread> kltList = new ArrayList<>();
            for (Object kltValue: klts){
                JSONObject klt = (JSONObject) kltValue;
                Integer kltId = klt.getInt("id");
                if (klt.getBoolean("ult")){
                    Integer algorithm = klt.getInt("algorithm");
                    JSONArray ults = klt.getJSONArray("ult");
                    List<UserLevelThread> ultList = new ArrayList<>();
                    for (Object ultValue: ults){
                        JSONObject ult = (JSONObject) ultValue;
                        Integer arrivalTimeULT = ult.getInt("arrival_time");
                        Integer ultId = ult.getInt("id");
                        List<Task> tasks = getTasks(ult.getJSONArray("tasks"));
                        ultList.add(new UserLevelThread((ArrayList<Task>) tasks,ultId,arrivalTimeULT));
                    }
                    kltList.add(new KernelLevelThread((ArrayList<UserLevelThread>) ultList,algorithm,kltId,id));
                }else{
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

}
