package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.GetTime;
import com.kidsbank.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the controller class for task_add.fxml ("Earn Money" -> "Open Tasks" -> "Add a Task"  view）
 */

public class TaskAddController {

    @FXML
    private Button addNewTaskButton;

    @FXML
    private Button cancelNewTaskButton;

    @FXML
    private TextField newTaskNameField;

    @FXML
    private TextField newTaskValueField;

    @FXML
    private Label errorInfo;

    private int taskId;

    @FXML
    private void cancelNewTask(){
        Scene currentScene = cancelNewTaskButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }
    }

    @FXML
    private void addNewTask(){
        if (StringUtil.isEmpty(newTaskNameField.getText())){
            errorInfo.setText("Task Name cannot be empty.");
            errorInfo.setVisible(true);
            return;
        }
        if (StringUtil.isEmpty(newTaskValueField.getText())){
            errorInfo.setText("Task Value cannot be empty.");
            errorInfo.setVisible(true);
            return;
        }
        if (!StringUtil.isNumeric(newTaskValueField.getText())){
            errorInfo.setText("Task Value must be numeric.");
            errorInfo.setVisible(true);
            return;
        }

        // account file location
        String csvFile = FileName.tasksFile;

        // generate taskID: initial value is 50001, new userID is max userID + 1.
        taskId = CSVFileHandler.getCSVRowCount(csvFile);
        if (taskId == 1) {
            taskId = 50001;
        } else {
            taskId = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(csvFile, 1)) +1;
        }

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        // get user account input data
        String[] data ;
        data = new String[]{
                String.valueOf(taskId),
                String.valueOf(newTaskNameField.getText()),
                String.valueOf(newTaskValueField.getText()),
                String.valueOf(userId),
                "Open", GetTime.getSystemTime(), GetTime.getSystemTime()};

        CSVFileHandler.addDataToCSV(csvFile, data);

        // 关闭当前窗口
        Scene currentScene = addNewTaskButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }

    }

}
