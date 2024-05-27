package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

/**
 * This is the controller class for task_open.fxml ("Earn Money" -> "Open Tasks" view）
 */

public class TaskOpenController {

    @FXML
    private TableView<TaskInfo> taskList;

    @FXML
    private TableColumn<TaskInfo,String> taskNoColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskNameColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskValueColumn;

    @FXML
    private TableColumn<TaskInfo, String> taskActionColumn;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button editTaskButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private Button refreshButton;

    @FXML
    public void initialize(){

        if (UserInfo.getInstance().getRole().equals("parent")){
            addTaskButton.setVisible(true);
            editTaskButton.setVisible(true);
            deleteTaskButton.setVisible(true);
        } else {
            addTaskButton.setVisible(false);
            editTaskButton.setVisible(false);
            deleteTaskButton.setVisible(false);
        }

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        List<TaskInfo> listOpenTask = CSVFileHandler.getTasksList(FileName.tasksFile, userId, 4, "Open", 5);
        taskNoColumn.setCellValueFactory(new PropertyValueFactory<>("taskNo"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskValueColumn.setCellValueFactory(new PropertyValueFactory<>("taskValue"));
        taskActionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        taskList.setItems(FXCollections.observableList(listOpenTask));

        taskList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // 设置选择模式为单选
    }

    @FXML
    private void addTask(){
        Main.opnNewView("view/task_add.fxml", "Kids Bank - Add a task");
    }

    @FXML
    private void editTask(){

        TaskInfo selectedItems = taskList.getSelectionModel().getSelectedItem();

        if (selectedItems != null){
            TaskEditController.setCurrent(selectedItems);
            Main.opnNewView("view/task_edit.fxml", "Kids Bank - Edit a task");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select one task for edition!");
            alert.showAndWait(); // 显示对话框，并等待用户关闭
            System.out.print("select is null");
            return;
        }

    }

    @FXML
    private void deleteTask(){
        //ObservableList<TaskInfo> data = taskList.getSelectionModel().getSelectedItems();

        TaskInfo selectedItems = taskList.getSelectionModel().getSelectedItem();

        if (selectedItems != null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this task?");

            // 添加按钮
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    CSVFileHandler.deleteSingleLine(FileName.tasksFile, selectedItems.getTaskId(), 1);
                    taskList.getItems().remove(selectedItems);
                }
            });

            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select one task for edition!");
            alert.showAndWait(); // 显示对话框，并等待用户关闭
            return;
        }
    }

    @FXML
    private void refreshTaskList(){
        initialize();
    }

    @FXML
    public void refreshMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(refreshButton);
    }

    @FXML
    public void refreshMouseExited() throws IOException {
        ButtonHandle.mouseExited(refreshButton);
    }

    @FXML
    public void addMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(addTaskButton);
    }

    @FXML
    public void addMouseExited() throws IOException {
        ButtonHandle.mouseExited(addTaskButton);
    }

    @FXML
    public void editMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(editTaskButton);
    }

    @FXML
    public void editMouseExited() throws IOException {
        ButtonHandle.mouseExited(editTaskButton);
    }

    @FXML
    public void deleteMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(deleteTaskButton);
    }

    @FXML
    public void deleteMouseExited() throws IOException {
        ButtonHandle.mouseExited(deleteTaskButton);
    }
}
