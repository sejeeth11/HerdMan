package provab.herdman.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by ptblr1035 on 29/7/16.
 */
public class Test {

    String phone_no;
    String farmer_name;
    LinkedHashSet<String> taskSet;
    LinkedHashMap<String, ArrayList<String>> linkedHashMap;

    public LinkedHashMap<String, ArrayList<String>> getLinkedHashMap() {
        return linkedHashMap;
    }

    public void setLinkedHashMap(LinkedHashMap<String, ArrayList<String>> linkedHashMap) {
        this.linkedHashMap = linkedHashMap;
    }

    public LinkedHashSet<String> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(LinkedHashSet<String> taskSet) {
        this.taskSet = taskSet;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    String task;
    ArrayList<String> arrayList;

   ArrayList<String> Task_array;

    public ArrayList<String> getTask_array() {
        return Task_array;
    }

    public void setTask_array(ArrayList<String> task_array) {
        Task_array = task_array;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
