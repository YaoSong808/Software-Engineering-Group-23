package com.kidsbank.controller;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AccountController {

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML
    private Label totalBalanceLabel, currentDepositLabel, fixedDepositLabel;

    @FXML
    private Label interestIncomeLabel, earnIncomeLabel, expenseLabel;

    @FXML
    private Pane headerPane;

    @FXML
    private void initialize(){

        // 根据用户角色，如果是parent角色，当前页面显示的都是默认child account的数据
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        String linkUserName = CSVFileHandler.getCSVSingleValue(FileName.accountFile, linkId, 1, 2);
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
            headerPane.setVisible(true);
            childDataPrompt.setVisible(true);
            childDataPrompt.setText("This is your default child account data (" + linkUserName +")!" );
            changeButton.setVisible(true);
        }

        // 从文件读取并运算页面上需要的显示值

        // 计算Total 的值，并显示
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        totalBalanceLabel.setText(String.format("%.2f", totalBalance));

        // 计算Fixed Deposit的值，并显示
        double fixedDepositValue = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
        fixedDepositLabel.setText(String.format("%.2f", fixedDepositValue));

        // 计算current deposit的值，并显示
        double currentDepositValue = totalBalance - fixedDepositValue;
        currentDepositLabel.setText(String.format("%.2f", currentDepositValue));

        // 计算interest income 的值，并显示
        double interestValue = CalculateBalance.getInterest(FileName.transactionFile, userId);
        interestIncomeLabel.setText(String.format("%.2f", interestValue));

        // 计算earned income 的值，并显示
        double totalIncome = CalculateBalance.getIncome(FileName.transactionFile, userId);
        double earnedIncome = totalIncome - interestValue;
        earnIncomeLabel.setText(String.format("%.2f", earnedIncome));

        // 计算expense 的值，并显示
        double expenseValue = CalculateBalance.getExpense(FileName.transactionFile, userId);
        expenseLabel.setText(String.format("%.2f", expenseValue));

        // 给Label加上Mouse click的效果
        ButtonHandle.labelClick(totalBalanceLabel);
        ButtonHandle.labelClick(currentDepositLabel);
        ButtonHandle.labelClick(fixedDepositLabel);
        ButtonHandle.labelClick(interestIncomeLabel);
        ButtonHandle.labelClick(earnIncomeLabel);
        ButtonHandle.labelClick(expenseLabel);

    }

    @FXML
    private void changeButtonAction() throws IOException {
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
            Button homeButton = (Button) currentScene.lookup("#accountButton");
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
}
