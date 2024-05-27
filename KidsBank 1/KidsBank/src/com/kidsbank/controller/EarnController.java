package com.kidsbank.controller;

import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EarnController {

    @FXML
    private Button openTaskButton;

    @FXML
    private Button completedTaskButton;

    @FXML
    private Button addTaskButton;

    @FXML
    private BorderPane taskPane;

    private void clearMenuBackground(){
        openTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
        completedTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
        addTaskButton.setStyle("-fx-background-color: #64A270;-fx-background-radius: 60;");
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
    private void addTaskAction(){
        clearMenuBackground();
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("task_add.fxml");
        taskPane.setCenter(view);
        addTaskButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

    }






}
