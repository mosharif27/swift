package com.SwiftCaseChallenge.Swift;

import java.util.List;

//Representing the collection of task IDs
public class TaskIdCollection {

    //declared as private to avoid other uses of it in other classes
    private List<TaskIdWrapper> taskIds;
    //getter method returns the list of taskIDS
    public List<TaskIdWrapper> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<TaskIdWrapper> taskIds) {
        this.taskIds = taskIds;
    }
}
