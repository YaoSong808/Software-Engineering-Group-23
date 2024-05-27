package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField account;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorInfoAccount;

    @FXML
    private Label errorInfoPassword;

    @FXML
    private Label errorInfoLoginFail;

    @FXML
    private Button signupButton;

    @FXML
    private Button loginButton;

    @FXML
    private void gotoSignupPage(){
        Main.changeView("view/signup.fxml", "Kids Bank - Sign up an account");   // 切换到Sign Up页面
    }

    @FXML
    private void mouseEnteredSignup(){
        ButtonHandle.mouseEntered(signupButton);
    }

    @FXML
    private void mouseExitedSignup(){
        ButtonHandle.mouseExited(signupButton);
    }

    @FXML
    private void mouseOverLogin(){
        ButtonHandle.mouseEntered(loginButton);
    }

    @FXML
    private void mouseExitLogin(){
        ButtonHandle.mouseExited(loginButton);
    }

    @FXML
    public void doLogin() {                           // 点击Login后执行的操作
        errorInfoAccount.setVisible(false);
        errorInfoPassword.setVisible(false);
        errorInfoLoginFail.setVisible(false);
        String accountText = account.getText();
        String passwordText = password.getText();

        // 判断email输入的合规性
        if (!StringUtil.isValidEmail(accountText) || (StringUtil.isEmpty(accountText)) ) {
            errorInfoAccount.setText("Email cannot be blank or the format is incorrect");
            errorInfoAccount.setVisible(true);
            return;
        }

        // 判断password输入的合规性
        if (StringUtil.isEmpty(passwordText) ) {
            errorInfoPassword.setText("Password cannot be blank");
            errorInfoPassword.setVisible(true);
            return;
        }

        if (VerifyAccount.isValidAccount(accountText, passwordText)) {       // 判断user和password是否正确

            //根据用户账号，获得用户的UserID和role,然后存到构造函数UserInfo里, 供后面随时需要check user role时使用

            String userId = CSVFileHandler.getCSVSingleValue(FileName.accountFile, accountText,2,1);
            String role = CSVFileHandler.getCSVSingleValue(FileName.accountFile, accountText, 2, 6);
            String linkId = CSVFileHandler.getCSVSingleValue(FileName.accountFile, accountText, 2, 8);
            UserInfo.getInstance().setUserInfo(userId, role, linkId);

            // 关闭当前的stage
            Scene currentScene = loginButton.getScene();
            if (currentScene != null) {
                Stage stage = (Stage) currentScene.getWindow();
                if (stage != null) {
                    stage.close();
                }
            }

            /* 判断账号的role和linkID, 确定显示页面
            *  Role=child, 直接进入常规页面
            *  Role=parent & LinkID=0, 说明该账号没有链接任何Child账号，所以会进入特殊页面，提示用户首先去link child account
            *  Role=parent & LinkID !=0, 说明已经链接child账号，linkID是默认的链接账号，此时会进入该链接child account的页面，同时拥有配置权限
            */

            String viewName;
            if (role.equals("parent") && (linkId == null|| linkId.equals("0"))){
                viewName = "no_child.fxml";
            } else {
                viewName = "home.fxml";
            }

            // 进入主页面
            try {
                Stage newStage = new Stage();
                newStage.setTitle("Kids Bank");

                // 加载FXML文件
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/menu.fxml"));
                Parent root = loader.load();

                // 创建新场景
                Scene scene = new Scene(root);

                PageLoader pageloader = new PageLoader();
                Pane view = pageloader.getPage(viewName);

                BorderPane borderPane = (BorderPane) scene.lookup("#mainPane");
                if (borderPane != null) {
                    borderPane.setCenter(view);
                }

                Button homeButton = (Button) scene.lookup("#homeButton");
                if (homeButton != null){
                    homeButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
                }

                // 设置舞台的场景
                newStage.setScene(scene);
                newStage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            errorInfoLoginFail.setText("Sorry, your email and/or password are incorrect. Please try again");
            errorInfoLoginFail.setVisible(true);
        }

    }
}