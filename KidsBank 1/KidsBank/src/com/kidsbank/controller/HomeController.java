package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.CalculateBalance;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


public class HomeController {

    @FXML
    private Label balanceLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label expenseLabel;

    @FXML
    private Label goalLabel;

    @FXML
    private Label goalPercentageLabel;

    @FXML
    private Label goalComment;

    @FXML
    private Button goalEditButton;

    @FXML
    private Button gotoEarnPageButton;

    @FXML
    private Button gotoGiftPageButton;

    @FXML
    private Button gotoDepositPageButton;

    @FXML
    private Label totalBalance;

    @FXML
    public void initialize(){
        // 开始从csv文件读出Home页面所要显示的数据
        // 拿到Balance 和Goal的值，并计算完成百分比
        double totalBalance =0;
        double goalValue=0;
        int percentage=0;
        totalBalance = CalculateBalance.getBalance(FileName.transactionFile, UserInfo.getInstance().getUserId());
        goalValue = Double.parseDouble(CSVFileHandler.getCSVSingleValue(FileName.accountFile, UserInfo.getInstance().getUserId(), 1, 7));
        if (goalValue > 0) {
            percentage = (int) ((totalBalance / goalValue) * 100);
        }

        // 把balance & goal & percentage的值填到当前的窗口
        balanceLabel.setText(Double.toString(totalBalance));

        double totalIncome = CalculateBalance.getIncome(FileName.transactionFile,3, UserInfo.getInstance().getUserId());
        incomeLabel.setText(Double.toString(totalIncome));


        double totalExpense = CalculateBalance.getExpense(FileName.transactionFile,3, UserInfo.getInstance().getUserId());
        expenseLabel.setText(Double.toString(totalExpense));

        goalLabel.setText(CSVFileHandler.getCSVSingleValue(FileName.accountFile, UserInfo.getInstance().getUserId(), 1, 7));

        goalPercentageLabel.setText("Reached " + Integer.toString(percentage) +"%");

        goalComment.setText("RMB " + Double.toString(totalBalance) + " out of " + Double.toString(goalValue));

        if ((goalEditButton != null) && UserInfo.getInstance().getRole().equals("child")) {
            goalEditButton.setVisible(false);
        }
    }

    @FXML
    public void gotoGoalEditor() throws IOException {
        Main.opnNewView("view/goal.fxml");
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
                homeButton.setStyle("-fx-background-color: #155434;");
            }

            // 增加Earn Menu的button背景色
            Button earnButton = (Button) currentScene.lookup("#earnButton");
            if (earnButton != null){
                earnButton.setStyle("-fx-background-color: #307e54;");
            }
        }
    }

    @FXML
    public void gotoGiftPage() throws IOException {

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("gift.fxml");

        Scene currentScene = gotoGiftPageButton.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载home page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Home Menu的button背景色清除
            Button homeButton = (Button) currentScene.lookup("#homeButton");
            if (homeButton != null){
                homeButton.setStyle("-fx-background-color: #155434;");
            }

            // 增加Gift Menu的button背景色
            Button giftButton = (Button) currentScene.lookup("#giftButton");
            if (giftButton != null){
                giftButton.setStyle("-fx-background-color: #307e54;");
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
                homeButton.setStyle("-fx-background-color: #155434;");
            }

            // 增加Deposit Menu的button背景色
            Button depositButton = (Button) currentScene.lookup("#depositButton");
            if (depositButton != null){
                depositButton.setStyle("-fx-background-color: #307e54;");
            }
        }
    }

}
