package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button homeButton;

    @FXML
    private Button earnButton;

    @FXML
    private Button giftButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button logoutButton;

    // 初始化菜单里每个Button的状态
    @FXML
    private void clearButtonSelection(){
        homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        earnButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        giftButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        depositButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        accountButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        profileButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
        settingButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
    }

    // 点击各个菜单后的执行
    @FXML
    public void homeButtonAction(){
        // 加载右边的Pane view
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("home.fxml");
            mainPane.setCenter(view);
            homeButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    public void earnButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("earn.fxml");
            mainPane.setCenter(view);
            earnButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void giftButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("gift.fxml");
            mainPane.setCenter(view);
            giftButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void depositButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("deposit.fxml");
            mainPane.setCenter(view);
            depositButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void accountButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("account.fxml");
            mainPane.setCenter(view);
            accountButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void profileButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("profile.fxml");
            mainPane.setCenter(view);
            profileButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void settingButtonAction(){
        if (UserInfo.getInstance().getRole().equals("parent") || UserInfo.getInstance().getRole().equals("child") ) {
            clearButtonSelection();
            PageLoader pageloader = new PageLoader();
            Pane view = pageloader.getPage("setting.fxml");
            mainPane.setCenter(view);
            settingButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
        }
    }

    @FXML
    private void backToLogin(){
        clearButtonSelection();
        Main.changeView("view/login.fxml");
        UserInfo.getInstance().clearUserInfo();

        // close the stage
        Scene currentScene = logoutButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }


    }
}
