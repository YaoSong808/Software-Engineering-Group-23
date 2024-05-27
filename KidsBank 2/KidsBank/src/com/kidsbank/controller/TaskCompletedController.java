package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class TaskCompletedController {

    @FXML
    private TableView<TaskInfo> taskList;

    @FXML
    private TableColumn<TaskInfo, String> taskNoColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskNameColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskValueColumn;

    @FXML
    private TableColumn<TaskInfo, String> completedTimeColumn;


    @FXML
    private void initialize() {

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        List<TaskInfo> listOpenTask = CSVFileHandler.getTasksList(FileName.tasksFile, userId, 4, "Done", 5);
        taskNoColumn.setCellValueFactory(new PropertyValueFactory<>("taskNo"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskValueColumn.setCellValueFactory(new PropertyValueFactory<>("taskValue"));
        completedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedTime"));
        taskList.setItems(FXCollections.observableList(listOpenTask));

    }
}

