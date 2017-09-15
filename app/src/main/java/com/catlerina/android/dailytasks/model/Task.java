package com.catlerina.android.dailytasks.model;


import java.util.Date;
import java.util.UUID;

public class Task {

    private UUID id;
    private String taskTitle;
    private Date taskDate;
    private boolean isDone;

    public Task(String taskTitle, Date taskDate, boolean isDone) {
        id = UUID.randomUUID();
        this.taskTitle = taskTitle;
        this.taskDate = taskDate;
        this.isDone = isDone;
    }

    public UUID getId() {
        return id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
