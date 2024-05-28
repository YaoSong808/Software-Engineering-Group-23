package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller class for deposit.fxml ("Fixed Deposit" view）
 */

public class DepositController {

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML Label fixedDepositLabel,depositIncomeLabel,avaliableDepositLabel;

    @FXML Label promptLabel, promptMessage, noDepositPrompt;

    @FXML TextField no1Interest, no2Interest, no3Interest, saveTimeName1, saveTimeName2, saveTimeName3,depositTransferValue;

    @FXML Pane headerPane;

    @FXML AnchorPane depositListPane;

    @FXML ChoiceBox depositChoiceBox;

    /**
     * Show the user's deposit/balance data and default UI in "Fixed Deposit" page based on user role
     */
    @FXML
    private void initialize(){

        checkDeposit();

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
            promptLabel.setVisible(true);

            // 设置对利息的编辑功能
            editInterest();

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
        String interestName1 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70000", 1, 3);
        String interestName2 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70001", 1, 3);
        String interestName3 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70002", 1, 3);
        String interest1 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70000", 1, 4);
        String interest2 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70001", 1, 4);
        String interest3 = CSVFileHandler.getCSVSingleValue(FileName.depositFile, "70002", 1, 4);
        saveTimeName1.setText(interestName1);
        saveTimeName2.setText(interestName2);
        saveTimeName3.setText(interestName3);
        no1Interest.setText(interest1);
        no2Interest.setText(interest2);
        no3Interest.setText(interest3);

        // 给Label加上Mouse click的效果
        ButtonHandle.labelClick(avaliableDepositLabel);
        ButtonHandle.labelClick(depositIncomeLabel);
        ButtonHandle.labelClick(fixedDepositLabel);

        // 给几个利息值设置颜色和字体，以及背景色
        no1Interest.setStyle("-fx-text-inner-color: #36da82; -fx-background-color: #64A270");
        no2Interest.setStyle("-fx-text-inner-color: #36da82; -fx-background-color: #64A270");
        no3Interest.setStyle("-fx-text-inner-color: #36da82; -fx-background-color: #64A270");
        saveTimeName1.setStyle("-fx-text-inner-color: white; -fx-background-color: #64A270");
        saveTimeName2.setStyle("-fx-text-inner-color: white; -fx-background-color: #64A270");
        saveTimeName3.setStyle("-fx-text-inner-color: white; -fx-background-color: #64A270");

        // 设置error message为invisible
        promptMessage.setVisible(false);

        // 将利息的默认期限，显示到下拉菜单里
        depositChoiceBox.setItems(FXCollections.observableArrayList(
                interestName1, interestName2, interestName3
        ));
        depositChoiceBox.setValue(interestName1); // 设置默认选中的值

        // 列出所有的固定存款记录

        List<String> depositList = new ArrayList<>();
        depositList = CSVFileHandler.getCSVMultipleValue2(FileName.transactionFile,userId,5, "FixedDeposit_Value", 1,4);

        if (depositList != null) {
            int i=0;
            int x=2; // pane位置的初始X轴的值
            int y=2;  // pane位置的初始Y轴的值

            for (i=depositList.size(); i>=1 ; i--) {
                String eventId = depositList.get(i-1);
                showDepositList(x, y, eventId, userId);
                y = y + 45;
            }
        }

        if (depositList.isEmpty()) {
            noDepositPrompt.setVisible(true);
        }


    }

    /**
     * Show one fixed deposit record(event) in "Fixed Deposit" page's deposit list.
     * The UI position is determined by the value of the x/y axis
     * @param x: x axis value
     * @param y: x axis value
     * @param eventId: the transaction's eventId
     * @param userId: userId
     */
    private void showDepositList(int x, int y, String eventId, String userId){

        // 增加一个pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #307E54; ");
        pane.setPrefSize(360, 38);
        pane.setLayoutX(x); // 设置左上角横坐标
        pane.setLayoutY(y); // 设置左上角纵坐标

        // 将new pane 加入到AchorPane
        depositListPane.getChildren().add(pane);

        // 将一个显示存款金额的label放到Pane里，并显示
        String depositValue = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Value",1, 3);

        Label depositAmount = new Label();
        depositAmount.setPrefSize(85, 38);
        depositAmount.setLayoutX(7);  //设置左上角横坐标
        depositAmount.setLayoutY(2);  //设置左上角纵坐标
        depositAmount.setStyle("-fx-font-family: System; -fx-font-size: 10px; -fx-font-weight: bold; ");
        depositAmount.setAlignment(Pos.CENTER);
        depositAmount.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(depositAmount);
        depositAmount.setText("RMB: "+depositValue+".00");

        // 将一个显示存款期限的label放到Pane里，并显示
        String depositDuration = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Duration",1, 3);

        Label depositDurationLabel = new Label();
        depositDurationLabel.setPrefSize(45, 38);
        depositDurationLabel.setLayoutX(100);  //设置左上角横坐标
        depositDurationLabel.setLayoutY(2);  //设置左上角纵坐标
        depositDurationLabel.setStyle("-fx-font-family: System; -fx-font-size: 10px;");
        depositDurationLabel.setAlignment(Pos.CENTER);
        depositDurationLabel.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(depositDurationLabel);
        depositDurationLabel.setText(depositDuration);

        // 将一个显示存款起始日期的label放到Pane里，并显示
        String depositStartDate = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_StartTime",1, 3);
        String depositEndDate = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_EndTime",1, 3);

        Label depositStartEndLabel = new Label();
        depositStartEndLabel.setPrefSize(100, 38);
        depositStartEndLabel.setLayoutX(170);  //设置左上角横坐标
        depositStartEndLabel.setLayoutY(2);  //设置左上角纵坐标
        depositStartEndLabel.setStyle("-fx-font-family: System; -fx-font-size: 10px;");
        depositStartEndLabel.setAlignment(Pos.CENTER);
        depositStartEndLabel.setTextFill(Color.WHITE); // 设置文本颜色
        depositStartEndLabel.setWrapText(true);
        pane.getChildren().add(depositStartEndLabel);
        depositStartEndLabel.setText(depositStartDate + " / "+ depositEndDate);


        // 如果一个存款是open状态，增加一个Withdraw button, 让用户可以提前取出存款
        String depositStatus = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Value",1, 2);

        if (depositStatus.equals("open") ) {

            // 将一个"Withdraw" button放到Pane里，用于让user提前支取存款
            Button withdrawButton = new Button("Withdraw");
            withdrawButton.setPrefSize(60, 25);
            withdrawButton.setLayoutX(280);
            withdrawButton.setLayoutY(6);
            withdrawButton.setStyle("-fx-background-radius: 15; -fx-font-family: System; -fx-font-size: 11px; -fx-background-color:#64A270;-fx-text-fill: WHITE;");
            pane.getChildren().add(withdrawButton);
            ButtonHandle.mouseEntered(withdrawButton);
            ButtonHandle.mouseExited(withdrawButton);

            // 给"Withdraw" button增加On Action. 更新文件，将deposit状态更新为"cancelled", 并刷新页面
            withdrawButton.setOnAction(event -> {

                // 确认对话框
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to withdraw the deposit?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // 确认后，跟新Transaction文件里，将该deposit的状态设置为close，并刷新页面, 同时向Transaction 文件里插入相关event数据
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {

                        // 更新Transaction文件, 将该条记录的FixedDeposit_Value的type改为“close"
                        CSVFileHandler.updateSingleDataToCSV2(FileName.transactionFile, "cancelled", 2, eventId, 4, "FixedDeposit_Value", 1);
                        CSVFileHandler.updateSingleDataToCSV2(FileName.transactionFile, GetTime.getSystemTime(), 7, eventId, 4, "FixedDeposit_Value", 1);

                        // 重新初始化
                        initialize();

                    }
                });

                // 清除所有的deposit list的Pane
                depositListPane.getChildren().clear();

                // 重新初始化
                initialize();
            });
        } else {

            // 增加一个Label, 显示”cancelled" or "finished"
            Label depositStatusLabel = new Label();
            depositStatusLabel.setPrefSize(60, 25);
            depositStatusLabel.setLayoutX(280);  //设置左上角横坐标
            depositStatusLabel.setLayoutY(6);  //设置左上角纵坐标
            depositStatusLabel.setStyle("-fx-font-family: System; -fx-font-size: 10px;");
            depositStatusLabel.setAlignment(Pos.CENTER);
            depositStatusLabel.setTextFill(Color.WHITE); // 设置文本颜色
            pane.getChildren().add(depositStatusLabel);
            depositStatusLabel.setText(depositStatus);
        }

    }

    /**
     * Check whether existing fixed deposit has reached the due date, if yes, update status and calculate the interest
     */
    private void checkDeposit(){

        //检查是否有到期的存款，如果有，把状态改为Finished，并把利息收入存入文件
        List<String> eventIdList = new ArrayList<>();
        eventIdList = CSVFileHandler.getCSVMultipleValue2(FileName.transactionFile,getUserId(),5, "open", 2,4);

        if (eventIdList != null) {
            int i=0;
            for (i=1; i<=eventIdList.size();i++) {
                String eventId = eventIdList.get(i-1);
                String depositEndTime = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_EndTime",1, 3);
                if (GetTime.compareWithCurrentTime(depositEndTime)){
                    CSVFileHandler.updateSingleDataToCSV2(FileName.transactionFile, "finished", 2, "FixedDeposit_Value", 1, eventId, 4);

                    //计算利息，并存入Transaction 文件
                    String depositYear = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Duration", 1, 3);
                    String depositPercentage = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_percentage", 1, 3);
                    String depositValue = CSVFileHandler.getCSVSingleValue2(FileName.transactionFile, eventId, 4, "FixedDeposit_Value", 1, 3);
                    double percent = Double.parseDouble(depositPercentage.replace("%", "")) / 100.0;
                    double interest = Double.parseDouble(depositYear) * percent * Double.parseDouble(depositValue);
                    String[] data = new String[]{ "FixedDeposit_Interest", "positive", String.valueOf(interest),String.valueOf(eventId),
                            getUserId(), GetTime.getSystemTime(), GetTime.getSystemTime()};
                    CSVFileHandler.addDataToCSV(FileName.transactionFile, data);

                }

            }
        }

    }

    /**
     * UI action for switching to the "Settings" page
     */
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

    /**
     * Edit the interest in "Fixed Deposit" page for a parent user
     */
    private void editInterest(){
        no1Interest.setEditable(true);
        no2Interest.setEditable(true);
        no3Interest.setEditable(true);

        // Parent账号可以对各个利息值的编辑功能 (添加对利息值变化事件监听器）, 并把新值保存到文件, 以及写入modified time
        no1Interest.setEditable(true);
        no2Interest.setEditable(true);
        no3Interest.setEditable(true);
        no1Interest.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 4, "70000", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70000", 1);
        });
        no2Interest.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 4, "70001", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70001", 1);
        });
        no3Interest.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 4, "70002", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70002", 1);
        });

        // Parent账号可以对各个利息名称进行编辑 (添加对利息名称变化事件监听器）, 并把新值保存到文件, 以及写入modified time
        saveTimeName1.setEditable(true);
        saveTimeName2.setEditable(true);
        saveTimeName3.setEditable(true);
        saveTimeName1.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 3, "70000", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70000", 1);
        });
        saveTimeName2.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 3, "70001", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70001", 1);
        });
        saveTimeName3.textProperty().addListener((observable, oldValue, newValue) -> {
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile,String.valueOf(newValue), 3, "70002", 1);
            CSVFileHandler.updateSingleDataToCSV(FileName.depositFile, GetTime.getSystemTime(), 6, "70002", 1);
        });


    }

    /**
     * UI action for transferring the input money amount to fixed deposit
     */
    @FXML
    private void transferButtonAction(){

        if (StringUtil.isEmpty(depositTransferValue.getText())) {
            promptMessage.setVisible(true);
            promptMessage.setStyle("-fx-text-fill: red;");
            promptMessage.setText("The deposit amount cannot be blank.");
            return;
        }

        String depositDuration = depositChoiceBox.getValue().toString();
        double depositValue = Double.parseDouble(depositTransferValue.getText());
        String userId = getUserId();

        // 计算available for transfer 的值
        double totalFixedDeposit = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        double availableDeposit  = totalBalance - totalFixedDeposit;

        if (depositValue > availableDeposit) {
            promptMessage.setVisible(true);
            promptMessage.setStyle("-fx-text-fill: red;");
            promptMessage.setText("You have no enough money to transfer.");
            return;
        }


        // generate eventID: initial value is 900001, new eventID is max userID + 1.
        int eventID = CSVFileHandler.getCSVRowCount(FileName.transactionFile);
        if (eventID == 1) {
            eventID = 900001;
        } else {
            eventID = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(FileName.transactionFile, 4)) +1;
        }

        // 将存款的数据写入文件
        String depositId = CSVFileHandler.getCSVSingleValue(FileName.depositFile, depositDuration, 3, 1);
        String depositYear = CSVFileHandler.getCSVSingleValue(FileName.depositFile, depositDuration, 3, 2);
        String depositInterest = CSVFileHandler.getCSVSingleValue(FileName.depositFile, depositDuration, 3, 4);


        // 增加6行数据到Transaction 文件
        String[] data1, data2, data3, data4, data5, data6 ;
        data1 = new String[]{ "FixedDeposit_Value", "open", depositTransferValue.getText(),String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        data2 = new String[]{ "FixedDeposit_ID", "id", depositId,String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        data3 = new String[]{ "FixedDeposit_Duration", "year", depositYear,String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        data4 = new String[]{ "FixedDeposit_StartTime", "time", GetTime.getSystemTime(),String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        data5 = new String[]{ "FixedDeposit_EndTime", "time", GetTime.getFutureTime(depositYear),String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        data6 = new String[]{ "FixedDeposit_percentage", "interest", depositInterest,String.valueOf(eventID),
                userId, GetTime.getSystemTime(), GetTime.getSystemTime()};
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data1);
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data2);
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data3);
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data4);
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data5);
        CSVFileHandler.addDataToCSV(FileName.transactionFile, data6);

        // 显示成功transfer的提示
        promptMessage.setVisible(true);
        promptMessage.setStyle("-fx-text-fill: #36da82;");
        promptMessage.setText("The deposit has been transferred to fixed deposit successfully!");

        // 重新初始化
        initialize();

    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void changeMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(changeButton);
    }

    /**
     * UI effect for mouse action
     */
    @FXML
    public void changeMouseExited() throws IOException {
        ButtonHandle.mouseExited(changeButton);
    }

    /**
     * Get child user's userID
     */
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
