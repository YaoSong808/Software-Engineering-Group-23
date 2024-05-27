package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountController {

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML
    private Label totalBalanceLabel, currentDepositLabel, fixedDepositLabel,noTransactionPrompt;

    @FXML
    private Label interestIncomeLabel, earnIncomeLabel, expenseLabel;

    @FXML
    private ChoiceBox transactionTypeChoiceBox, transactionTimeChoiceBox;

    @FXML
    private AnchorPane transactionListPane;

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

        // 将Transaction的种类，显示到下拉菜单里，并设置默认值为"All"
        transactionTypeChoiceBox.setItems(FXCollections.observableArrayList(
                "All", "Earn Money", "Redeem Gifts", "Deposit Interest"
        ));
        transactionTypeChoiceBox.setValue("All");


        // 将Transaction的时间段，显示到下拉菜单里，并设置默认值为"Last 3 months",同时更加listen去监听choiceBox的值的变化
        transactionTimeChoiceBox.setItems(FXCollections.observableArrayList(
                "Last 3 months", "Last 6 months", "Last 1 year", "All"
        ));
        transactionTimeChoiceBox.setValue("Last 3 months");

        // 默认的Transaction List
        getTransactionList(transactionTimeChoiceBox.getValue().toString(), transactionTypeChoiceBox.getValue().toString());

        // 添加Transaction 时间段选择的ChoiceBox的监听器
        transactionTimeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // 清除所有的transaction list的Pane
            transactionListPane.getChildren().clear();

            // 重新根据选项来刷新transaction list
            getTransactionList(newValue.toString(), transactionTypeChoiceBox.getValue().toString());

        });

        // 添加Transaction种类的ChoiceBox的监听器
        transactionTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // 清除所有的transaction list的Pane
            transactionListPane.getChildren().clear();

            // 重新根据选项来刷新transaction list
            getTransactionList(transactionTypeChoiceBox.getValue().toString(), newValue.toString());
        });

    }

    private void getTransactionList (String duration, String category){

        // 列出所有符合要求的Transaction记录的eventID, 然后调用showTransactionList的函数把eventID对应的transaction记录展示出来

        //根据输入的category，确定显示的种类
        String columnValue21="", columnValue22="", columnValue23="";
        switch (category) {
            case "All":
                columnValue21 = "Implement_Tasks";
                columnValue22 = "Redeem_Gifts";
                columnValue23 = "FixedDeposit_Interest";
                break;
            case "Earn Money":
                columnValue21 = "Implement_Tasks";
                break;
            case "Redeem Gifts":
                columnValue22 = "Redeem_Gifts";
                break;
            case "Deposit Interest":
                columnValue23 = "FixedDeposit_Interest";
                break;
            default:
                columnValue21 = "Implement_Tasks";
                columnValue22 = "Redeem_Gifts";
                columnValue23 = "FixedDeposit_Interest";
        }

        // 根据输入的duration, 确定显示的月份
        int months;
        switch (duration){
            case "Last 3 months":
                months = 3;
                break;
            case "Last 6 months":
                months = 6;
                break;
            case "Last 1 year":
                months = 12;
                break;
            case "All":
                months = 100000;
                break;
            default:
                months = 3;
        }


        List<String>  transactionList = new ArrayList<>();
        transactionList = CSVFileHandler.getCSVMultipleValue3(FileName.transactionFile,
                getUserId(), 5, columnValue21, columnValue22, columnValue23, 1,
                months,7,4);

        if (transactionList != null) {
            int i=0;
            int x=2; // pane位置的初始X轴的值
            int y=2;  // pane位置的初始Y轴的值

            for (i=transactionList.size(); i>=1 ; i--) {
                String eventId = transactionList.get(i-1);
                showTransactionList(x, y, eventId);
                y = y + 35;
            }
        }

        if (transactionList.isEmpty()) {
            noTransactionPrompt.setVisible(true);
        }
    }

    private void showTransactionList(int x, int y, String eventId) {

        // 获得transaction记录的值：时间、名称、类别、金额
        String transactionTime=null, transactionName=null, transactionType=null, transactionValue=null;

        String transactionTaskTime = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Implement_Tasks",1, 7);
        String transactionGiftTime = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Redeem_Gifts",1, 7);
        String transactionDepositTime = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Interest",1, 7);

        String transactionTaskId = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Implement_Tasks_ID",1, 3);
        String transactionTaskName = CSVFileHandler.getCSVSingleValue(FileName.tasksFile, transactionTaskId,1,2);
        String transactionGiftId = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Redeem_Gifts_ID",1, 3);
        String transactionGiftName = CSVFileHandler.getCSVSingleValue(FileName.giftsFile, transactionGiftId,1,2);
        String transactionDepositYear = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Duration",1, 3);

        String transactionTaskValue = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Implement_Tasks",1, 3);
        String transactionGiftValue = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "Redeem_Gifts",1, 3);
        String transactionInterestValue = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Interest",1, 3);

        if (transactionTaskValue != null) {
            transactionTime = GetTime.convertTimeFormat(transactionTaskTime);
            transactionName = transactionTaskName;
            transactionType = "Earn money";
            transactionValue = "+ RMB " + transactionTaskValue;

        }

        if (transactionGiftValue != null) {
            transactionTime = GetTime.convertTimeFormat(transactionGiftTime);
            transactionName = transactionGiftName;
            transactionType = "Redeem gifts";
            transactionValue = "- RMB " + transactionGiftValue;
        }

        if (transactionInterestValue != null) {
            transactionTime = GetTime.convertTimeFormat(transactionDepositTime);
            transactionName = "Interest for fixed deposit (" + transactionDepositYear +" year)";
            transactionType = "Interest";
            transactionValue = "+ RMB " + transactionInterestValue;

        }

        // 增加一个pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #307E54; ");
        pane.setPrefSize(550, 30);
        pane.setLayoutX(x); // 设置左上角横坐标
        pane.setLayoutY(y); // 设置左上角纵坐标

        // 将new pane 加入到AchorPane
        transactionListPane.getChildren().add(pane);

        // 在每个Pane里显示一条transaction记录：时间、名称、类别、金额。 每个内容都现在在一个label类型里。
        // 在Pane里增加Time 列
        Label transactionTimeLabel = new Label();
        transactionTimeLabel.setPrefSize(75, 30);
        transactionTimeLabel.setLayoutX(2);  //设置左上角横坐标
        transactionTimeLabel.setLayoutY(2);  //设置左上角纵坐标
        transactionTimeLabel.setStyle("-fx-font-family: System; -fx-font-size: 11px;  ");
        transactionTimeLabel.setAlignment(Pos.CENTER);
        transactionTimeLabel.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(transactionTimeLabel);
        transactionTimeLabel.setText(transactionTime);

        // 在Pane里增加Name 列
        Label transactionNameLabel = new Label();
        transactionNameLabel.setPrefSize(260, 30);
        transactionNameLabel.setLayoutX(80);  //设置左上角横坐标
        transactionNameLabel.setLayoutY(2);  //设置左上角纵坐标
        transactionNameLabel.setStyle("-fx-font-family: System; -fx-font-size: 11px; -fx-font-weight: bold;  ");
        transactionNameLabel.setAlignment(Pos.CENTER_LEFT);
        transactionNameLabel.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(transactionNameLabel);
        transactionNameLabel.setText(transactionName);

        // 在Pane里增加Type 列
        Label transactionTypeLabel = new Label();
        transactionTypeLabel.setPrefSize(80, 30);
        transactionTypeLabel.setLayoutX(340);  //设置左上角横坐标
        transactionTypeLabel.setLayoutY(2);  //设置左上角纵坐标
        transactionTypeLabel.setStyle("-fx-font-family: System; -fx-font-size: 11px;  ");
        transactionTypeLabel.setAlignment(Pos.CENTER_LEFT);
        transactionTypeLabel.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(transactionTypeLabel);
        transactionTypeLabel.setText(transactionType);

        // 在Pane里增加Value 列
        Label transactionValueLabel = new Label();
        transactionValueLabel.setPrefSize(80, 30);
        transactionValueLabel.setLayoutX(425);  //设置左上角横坐标
        transactionValueLabel.setLayoutY(2);  //设置左上角纵坐标
        transactionValueLabel.setStyle("-fx-font-family: System; -fx-font-size: 11px; -fx-font-weight: bold; ");
        transactionValueLabel.setAlignment(Pos.CENTER_LEFT);
        transactionValueLabel.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(transactionValueLabel);
        transactionValueLabel.setText(transactionValue);

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

    private String getUserId(){
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }
        return userId;
    }
}
