package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EarnController {

    @FXML
    private Button openTaskButton;

    @FXML
    private Button completedTaskButton;

    @FXML
    private Button pendingTaskButton;

    @FXML
    private BorderPane taskPane;

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML
    private void initialize(){
        // Load Tasks List
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("task_open.fxml");
        taskPane.setCenter(view);
        openTaskButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

        // 根据用户角色，如果是parent角色，当前页面显示的都是默认child account的数据
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        String linkUserName = CSVFileHandler.getCSVSingleValue(FileName.accountFile, linkId, 1, 2);
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
            childDataPrompt.setVisible(true);
            childDataPrompt.setText("This is your default child account data (" + linkUserName +")!" );
            changeButton.setVisible(true);
        }

    }

    private void clearMenuBackground(){
        openTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
        completedTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
        pendingTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
    }

    @FXML
    private void openTaskAction(){
        clearMenuBackground();
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("task_open.fxml");
        taskPane.setCenter(view);
        openTaskButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

    }

    @FXML
    private void completedTaskAction(){
        clearMenuBackground();
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("task_completed.fxml");
        taskPane.setCenter(view);
        completedTaskButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

    }

    @FXML
    private void pendingTaskAction(){
        clearMenuBackground();
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("task_pending.fxml");
        taskPane.setCenter(view);
        pendingTaskButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

    }

    @FXML
    private void changeButtonAction() throws IOException{
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("setting.fxml");

        Scene currentScene = changeButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载home page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#earnButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Setting Menu的button背景色
            Button depositButton = (Button) currentScene.lookup("#settingButton");
            if (depositButton != null){
                depositButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }

    }

    @FXML
    public void changeMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(changeButton);
    }

    @FXML
    public void changeMouseExited() throws IOException {
        ButtonHandle.mouseExited(changeButton);
    }

    @FXML
    public void openMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(openTaskButton);
    }

    @FXML
    public void openMouseExited() throws IOException {
        ButtonHandle.mouseExited(openTaskButton);
    }

    @FXML
    public void completedMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(completedTaskButton);
    }

    @FXML
    public void completedMouseExited() throws IOException {
        ButtonHandle.mouseExited(completedTaskButton);
    }

    @FXML
    public void pendingMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(pendingTaskButton);
    }

    @FXML
    public void pendingMouseExited() throws IOException {
        ButtonHandle.mouseExited(pendingTaskButton);
    }
}