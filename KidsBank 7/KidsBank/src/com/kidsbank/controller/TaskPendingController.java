package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.GetTime;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

/**
 * This is the controller class for task_pending.fxml ("Earn Money" -> "Pending Tasks" view）
 */

public class TaskPendingController {

    @FXML
    private TableView<TaskInfo> taskList;

    @FXML
    private TableColumn<TaskInfo, String> taskNoColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskNameColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskValueColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskStatusColumn;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Button refreshButton;

    /**
     * Show the pending tasks list and default UI in "Earn Money" -> "Pending Tasks" page based on user role
     */
    @FXML
    private void initialize (){

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        List<TaskInfo> listPendingTask = CSVFileHandler.getTasksList(FileName.tasksFile, userId, 4, "Pending Approval", 5);
        List<TaskInfo> listPendingTask2 = CSVFileHandler.getTasksList(FileName.tasksFile, userId, 4, "Rejected", 5);
        listPendingTask.addAll(listPendingTask2);

        // 把组和后的list里，taskNo重新设置为顺序号
        int i=1;
        for (TaskInfo item : listPendingTask) {
            item.setTaskNo(i);
            i++;
        }

        // 设置tableView并显示
        taskNoColumn.setCellValueFactory(new PropertyValueFactory<>("taskNo"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskValueColumn.setCellValueFactory(new PropertyValueFactory<>("taskValue"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        taskList.setItems(FXCollections.observableList(listPendingTask));

        if (UserInfo.getInstance().getRole().equals("parent")){
            approveButton.setVisible(true);
            rejectButton.setVisible(true);
        }

        if (UserInfo.getInstance().getRole().equals("child")){
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
        }

    }

    /**
     * Approved the task and transfer the earned money to child user.
     */
    @FXML
    private void approveAction(){

        TaskInfo selectedItems = taskList.getSelectionModel().getSelectedItem();

        if (selectedItems != null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to approve this task?" );

            // 添加按钮
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    // 更新Task文件里，task的状态为Done, 并更新modified time
                    String csvFile = FileName.tasksFile;
                    String taskId = selectedItems.getTaskId();
                    String modifiedTime = GetTime.getSystemTime();
                    CSVFileHandler.updateSingleDataToCSV(csvFile, "Done", 5, taskId, 1);
                    CSVFileHandler.updateSingleDataToCSV(csvFile, modifiedTime, 7, taskId, 1);

                    // 增加2行数据到 transaction文件
                    // generate eventID: initial value is 900001, new eventID is max userID + 1.
                    int eventID = CSVFileHandler.getCSVRowCount(csvFile);
                    if (eventID == 1) {
                        eventID = 900001;
                    } else {
                        eventID = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(FileName.transactionFile, 4)) +1;
                    }

                    String userId = UserInfo.getInstance().getUserId();
                    String role = UserInfo.getInstance().getRole();
                    String linkId = UserInfo.getInstance().getLinkId();
                    if (role.equals("parent") && linkId != null && !linkId.equals("0")){
                        userId = linkId;
                    }

                    String[] data1 ;
                    data1 = new String[]{ "Implement_Tasks", "positive",
                            selectedItems.getTaskValue(),
                            String.valueOf(eventID),
                            userId,
                            GetTime.getSystemTime(), GetTime.getSystemTime()};
                    CSVFileHandler.addDataToCSV(FileName.transactionFile, data1);

                    String[] data2 ;
                    data2 = new String[]{ "Implement_Tasks_ID", "id",
                            selectedItems.getTaskId(),
                            String.valueOf(eventID),
                            userId,
                            GetTime.getSystemTime(), GetTime.getSystemTime()};
                    CSVFileHandler.addDataToCSV(FileName.transactionFile, data2);
                }
            });

            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select one task for approval!");
            alert.showAndWait(); // 显示对话框，并等待用户关闭
            return;
        }
    }

    /**
     * Change selected task to rejected status after the user click "Reject" button in the "Pending Tasks" page.
     */
    @FXML
    private void rejectAction(){

        TaskInfo selectedItems = taskList.getSelectionModel().getSelectedItem();

        if (selectedItems != null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to reject this task?" + "\n"+ "\n" +"Note: You can select rejected task to approve it later." );

            // 添加按钮
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    String csvFile = FileName.tasksFile;
                    String taskId = selectedItems.getTaskId();
                    String modifiedTime = GetTime.getSystemTime();
                    CSVFileHandler.updateSingleDataToCSV(csvFile, "Rejected", 5, taskId, 1);
                    CSVFileHandler.updateSingleDataToCSV(csvFile, modifiedTime, 7, taskId, 1);
                }
            });

            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select one task for approval!");
            alert.showAndWait(); // 显示对话框，并等待用户关闭
            return;
        }
    }

    /**
     * Refresh Tasks List
     */
    @FXML
    private void refreshAction(){
        initialize();
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void refreshMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(refreshButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void refreshMouseExited() throws IOException {
        ButtonHandle.mouseExited(refreshButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void approveMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(approveButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void approveMouseExited() throws IOException {
        ButtonHandle.mouseExited(approveButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void rejectMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(rejectButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void rejectMouseExited() throws IOException {
        ButtonHandle.mouseExited(rejectButton);
    }

}

