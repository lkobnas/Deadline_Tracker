//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Model;

public class ToDoModel implements  Comparable<ToDoModel>{
    private int id;
    private int status;
    private int importance;
    private String task, deadline;

    public ToDoModel(){

    }
    public ToDoModel(int id, int status, int importance, String task, String deadline) {
        this.id = id;
        this.status = status;
        this.importance = importance;
        this.task = task;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDeadline(){
        return deadline;
    }

    public void setDeadline(String deadline){
        this.deadline = deadline;
    }

    public int getImportance(){
        return importance;
    }

    public void setImportance(int importance){
        this.importance = importance;
    }

    public String getStringId(){ return Integer.toString(id);}

    public String getStringStatus(){ return Integer.toString(status);}

    public String getStringImportance(){ return Integer.toString(importance);}
    @Override
    public int compareTo(ToDoModel toDoModel) {
        if(this.getStatus()>toDoModel.getStatus()){
            return 1;
        }else{
            return 0;
        }
    }
}
