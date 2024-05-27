package com.kidsbank.entity;

import javafx.scene.control.Button;

/**
 * This entity class is to define the tasks properties for "Earn Money" module
 */

public class TaskInfo {


    private int taskNo;

    private String taskId;

    private String taskName;

    private String taskValue;

    private String taskStatus;

    private String modifiedTime;

    private Button actionButton;

    public TaskInfo(){

    }
    public TaskInfo(int taskNo, String taskId, String taskName, String taskValue, String taskStatus,String modifiedTime, Button actionButton) {
        this.taskNo = taskNo;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskValue = taskValue;
        this.taskStatus = taskStatus;
        this.modifiedTime = modifiedTime;
        this.actionButton = actionButton;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskValue() {
        return taskValue;
    }

    public void setTaskValue(String taskValue) {
        this.taskValue = taskValue;
    }

}
