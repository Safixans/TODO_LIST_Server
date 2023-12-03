package dao;

import java.util.Date;

public class Task {
    private int id;
    private String task_name;
    private boolean status;






    // Constructor for creating a new task (without id, as it's auto-generated in the database)
    public Task(String task_name, boolean status) {
        this.task_name = task_name;
        this.status = status;

    }


    // Constructor with id, useful for updating and deleting tasks
    public Task(int id, String task_name, boolean status) {
        this.id = id;
        this.task_name = task_name;
        this.status = status;

    }

    // Default constructor

    public Task() {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}

