package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.CalculateBalance;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;


public class HomeController {

    @FXML
    private Label balanceLabel, incomeLabel,expenseLabel,increaseLabel;

    @FXML
    private Label goalLabel,goalPercentageLabel,goalComment;

    @FXML
    private Button goalEditButton,refreshButton;

    @FXML
    private Button gotoEarnPageButton,gotoGiftPageButton, gotoDepositPageButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML
    private void initialize(){
        // 开始从csv文件读出Home页面所要显示的数据
        // 拿到Balance 和Goal的值，并计算完成百分比
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

        int percentage=0;
        int increasePercentage=0;
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        double balanceForMonthAgo = CalculateBalance.getBalanceForOneMonthAgo(FileName.transactionFile, userId);
        double goalValue = Double.parseDouble(CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId, 1, 7));
        if (goalValue > 0) {
            percentage = (int) ((totalBalance / goalValue) * 100);
            progressBar.setProgress(totalBalance/goalValue);
        }
        if (balanceForMonthAgo > 0) {
            increasePercentage = (int)(((totalBalance-balanceForMonthAgo)/balanceForMonthAgo) * 100);
        }

        // 把balance & goal & percentage & increased% 的值填到当前的窗口
        balanceLabel.setText(Double.toString(totalBalance));

        double totalIncome = CalculateBalance.getIncome(FileName.transactionFile, userId);
        incomeLabel.setText(Double.toString(totalIncome));

        double totalExpense = CalculateBalance.getExpense(FileName.transactionFile, userId);
        expenseLabel.setText(Double.toString(totalExpense));

        increaseLabel.setText(Integer.toString(increasePercentage) + "%");

        goalLabel.setText(CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId, 1, 7));

        goalPercentageLabel.setText("Reached " + Integer.toString(percentage) +"%");

        goalComment.setText("RMB " + Double.toString(totalBalance) + " out of " + Double.toString(goalValue));

        if ((goalEditButton != null) && UserInfo.getInstance().getRole().equals("child")) {
            goalEditButton.setVisible(false);
            refreshButton.setVisible(false);
        }

        ButtonHandle.labelClick(balanceLabel);
        ButtonHandle.labelClick(incomeLabel);
        ButtonHandle.labelClick(expenseLabel);
        ButtonHandle.labelClick(increaseLabel);

    }

    @FXML
    public void gotoGoalEditor() throws IOException {
        Main.opnNewView("view/goal.fxml","Kids Bank - Set a saving goal");
    }
    public void gotoAccount() throws IOException {

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("account.fxml");

        Scene currentScene = gotoEarnPageButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载account page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#homeButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Account Menu的button背景色
            Button earnButton = (Button) currentScene.lookup("#accountButton");
            if (earnButton != null){
                earnButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }
    }

    @FXML
    public void gotoEarnPage() throws IOException {

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("earn.fxml");

        Scene currentScene = gotoEarnPageButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载home page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#homeButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Earn Menu的button背景色
            Button earnButton = (Button) currentScene.lookup("#earnButton");
            if (earnButton != null){
                earnButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }
    }

    @FXML
    public void gotoGiftPage() throws IOException {

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("gift.fxml");

        Scene currentScene = gotoGiftPageButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载gift page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#homeButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Gift Menu的button背景色
            Button giftButton = (Button) currentScene.lookup("#giftButton");
            if (giftButton != null){
                giftButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }
    }

    @FXML
    public void gotoDepositPage() throws IOException {

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("deposit.fxml");

        Scene currentScene = gotoDepositPageButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载home page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#homeButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Deposit Menu的button背景色
            Button depositButton = (Button) currentScene.lookup("#depositButton");
            if (depositButton != null){
                depositButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }
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
            Button homeButton = (Button) currentScene.lookup("#homeButton");
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
    public void editorMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(goalEditButton);
    }

    @FXML
    public void editorMouseExited() throws IOException {
        ButtonHandle.mouseExited(goalEditButton);
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
    public void refreshButtonAction() throws IOException {
        initialize();
    }

    @FXML
    public void earnMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(gotoEarnPageButton);
    }

    @FXML
    public void earnMouseExited() throws IOException {
        ButtonHandle.mouseExited(gotoEarnPageButton);
    }

    @FXML
    public void giftMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(gotoGiftPageButton);
    }

    @FXML
    public void giftMouseExited() throws IOException {
        ButtonHandle.mouseExited(gotoGiftPageButton);
    }

    @FXML
    public void depositMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(gotoDepositPageButton);
    }

    @FXML
    public void depositMouseExited() throws IOException {
        ButtonHandle.mouseExited(gotoDepositPageButton);
    }

}