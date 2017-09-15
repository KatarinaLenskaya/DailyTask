package com.catlerina.android.dailytasks.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskKeeper {
    private static TaskKeeper taskKeeper;

    private List<Task> tasks;

    public static TaskKeeper getInstance() {
        if (taskKeeper == null) {
            taskKeeper = new TaskKeeper();
        }
        return taskKeeper;
    }

    private TaskKeeper(){
        tasks = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.set(2017, 8, 13);
        Date date = c.getTime();

        //hardcoded values
        tasks.add(new Task("Task1", date, false ));
        tasks.add(new Task("Task2", new Date(), false ));
        tasks.add(new Task("Task3", new Date(), false ));
        tasks.add(new Task("Task4", new Date(), false ));
        c.set(2017, 9, 13);
        date = c.getTime();
        tasks.add(new Task("Task5", date, false ));
        c.set(2017, 9, 26);
        date = c.getTime();
        tasks.add(new Task("Task6", date, false ));
        c.set(2017, 10, 1);
        date = c.getTime();
        tasks.add(new Task("Task7", date, false ));
        c.set(2017, 8, 30);
        date = c.getTime();
        tasks.add(new Task("Task8", date, false ));
        tasks.add(new Task("Task9", null, false ));
        tasks.add(new Task("Task10", new Date(), false ));
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public Task getTask(UUID id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }
}
