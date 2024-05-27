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

public class DepositController {

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML Label fixedDepositLabel,depositIncomeLabel,avaliableDepositLabel;

    @FXML Label no1Interest, no2Interest, no3Interest;

    @FXML
    private void initialize(){

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

        // 从文件读取并运算页面上需要的显示值
        // 计算Total Deposit的值，并显示
        double totalFixedDeposit = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
        fixedDepositLabel.setText(Double.toString(totalFixedDeposit));

        // 计算Interest Income的值，并显示
        double interestValue = CalculateBalance.getInterest(FileName.transactionFile, userId);
        depositIncomeLabel.setText(Double.toString(interestValue));

        // 计算Available to transfer 的值，并显示
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        double availableDeposit  = totalBalance - totalFixedDeposit;
        avaliableDepositLabel.setText(Double.toString(availableDeposit));

        // 读取文件里的利息值，并显示
        String interest1 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70000", 1, 3);
        String interest2 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70001", 1, 3);
        String interest3 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70002", 1, 3);
        no1Interest.setText(interest1);
        no2Interest.setText(interest2);
        no3Interest.setText(interest3);

        // 给Label加上Mouse click的效果
        ButtonHandle.labelClick(avaliableDepositLabel);
        ButtonHandle.labelClick(depositIncomeLabel);
        ButtonHandle.labelClick(fixedDepositLabel);
        ButtonHandle.labelClick(no1Interest);
        ButtonHandle.labelClick(no2Interest);
        ButtonHandle.labelClick(no3Interest);


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
            Button homeButton = (Button) currentScene.lookup("#depositButton");
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
