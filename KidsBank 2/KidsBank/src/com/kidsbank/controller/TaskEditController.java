package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.GetTime;
import com.kidsbank.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskEditController {

    @FXML
    private TextField updateTaskNameField;

    @FXML
    private TextField updateTaskValueField;

    @FXML
    private Button cancelUpdateTaskButton;

    @FXML
    private Button updateTaskButton;

    @FXML
    private Label errorInfo;

    private static TaskInfo current;

    public static void setCurrent(TaskInfo current){

        TaskEditController.current = current;
    }

    @FXML
    private void initialize(){
        updateTaskNameField.setText(current.getTaskName());
        updateTaskValueField.setText(current.getTaskValue());
    }

    @FXML
    private void updateTask(){
        if (StringUtil.isEmpty(updateTaskNameField.getText())){
            errorInfo.setText("Task Name cannot be empty.");
            errorInfo.setVisible(true);
            return;
        }
        if (StringUtil.isEmpty(updateTaskValueField.getText())){
            errorInfo.setText("Task Value cannot be empty.");
            errorInfo.setVisible(true);
            return;
        }
        if (!StringUtil.isNumeric(updateTaskValueField.getText())){
            errorInfo.setText("Task Value must be numeric.");
            errorInfo.setVisible(true);
            return;
        }

        // new value
        String csvFile = FileName.tasksFile;
        String taskId = current.getTaskId();
        String taskName = updateTaskNameField.getText();
        String taskValue = updateTaskValueField.getText();

        // update the task
        CSVFileHandler.updateSingleDataToCSV(csvFile,taskName,2,taskId,1);
        CSVFileHandler.updateSingleDataToCSV(csvFile,taskValue,3, taskId,1);
        CSVFileHandler.updateSingleDataToCSV(csvFile,GetTime.getSystemTime(),7, taskId,1);

        // 关闭当前窗口
        Scene currentScene = updateTaskButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }

    }

    @FXML
    private void cancelUpdateTask(){

        Scene currentScene = cancelUpdateTaskButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }

    }



}
