package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the controller class for menu.fxml (Left Menu view in main window）
 */

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


    /**
     * Show the left navigation menu and default UI in main window based on user role
     */
    @FXML
    private void initialize(){
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && (linkId == null|| linkId.equals("0"))){
            if (earnButton != null){
                earnButton.setVisible(false);
            }
            if (giftButton != null){
                giftButton.setVisible(false);
            }
            if (depositButton != null){
                depositButton.setVisible(false);
            }
            if (accountButton != null){
                accountButton.setVisible(false);
            }
        }

    }

    /**
     * Initialize all buttons in the left menu.
     */
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

    /**
     * UI action for clicking the "Home" button in the left menu
     */
    // 点击各个菜单后的执行
    @FXML
    public void homeButtonAction(){
        // 加载右边的Pane view
        String viewName;
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && (linkId == null|| linkId.equals("0"))){
            viewName = "no_child.fxml";
        } else {
            viewName = "home.fxml";
        }

        clearButtonSelection();
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage(viewName);
        mainPane.setCenter(view);
        homeButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");

    }

    /**
     * UI action for clicking the "Earn Money" button in the left menu
     */
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

    /**
     * UI action for clicking the "Redeem Gift" button in the left menu
     */
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

    /**
     * UI action for clicking the "Fixed Deposit" button in the left menu
     */
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

    /**
     * UI action for clicking the "My Account" button in the left menu
     */
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

    /**
     * UI action for clicking the "Home" button in the left menu
     */
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

    /**
     * UI action for clicking the "Settings" button in the left menu
     */
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

    /**
     * UI action for clicking the "Logout" button in the left menu
     */
    @FXML
    private void backToLogin(){
        clearButtonSelection();
        Main.changeView("view/login.fxml", "Kids Bank - Login");
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

    /**
     * UI effect for mouse action
     */
    @FXML
    public void homeMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(homeButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void homeMouseExited() throws IOException {
        ButtonHandle.mouseExited(homeButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void earnMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(earnButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void earnMouseExited() throws IOException {
        ButtonHandle.mouseExited(earnButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void giftMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(giftButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void giftMouseExited() throws IOException {
        ButtonHandle.mouseExited(giftButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void depositMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(depositButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void depositMouseExited() throws IOException {
        ButtonHandle.mouseExited(depositButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void accountMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(accountButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void accountMouseExited() throws IOException {
        ButtonHandle.mouseExited(accountButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void profileMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(profileButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void profileMouseExited() throws IOException {
        ButtonHandle.mouseExited(profileButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void settingMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(settingButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void settingMouseExited() throws IOException {
        ButtonHandle.mouseExited(settingButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void logoutMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(logoutButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void logoutMouseExited() throws IOException {
        ButtonHandle.mouseExited(logoutButton);
    }
}
