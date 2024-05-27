package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.StringUtil;
import com.kidsbank.util.VerifyAccount;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingController {

    @FXML
    private CheckBox darkModeBox;

    @FXML
    private ChoiceBox languageBox;

    @FXML
    private ChoiceBox timezoneBox;

    @FXML
    private TextField accountField;

    @FXML
    private TextField linkCodeField;

    @FXML
    private Button linkButton;

    @FXML
    private Label errorInfo;

    @FXML
    private Label promptMessage;

    @FXML
    private Label link1,link2,link3,link4,link5,link1Status, link2Status, link3Status,link4Status, link5Status, note;

    @FXML
    private Button unlink1, unlink2, unlink3, unlink4, unlink5, default1, default2, default3, default4, default5;

    @FXML
    private Button linkTitle;

    @FXML
    private Line line,line2;

    @FXML
    private Label linkSubTitle, linkStatus, linkActions;

    @FXML
    private void initialize(){

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();

        //清楚所有的Message
        errorInfo.setVisible(false);
        promptMessage.setVisible(false);


        // 初始化Dark Mode的值
        String mode = CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId,1, 9);
        if (mode.equals("dark")){
            darkModeBox.setSelected(true);
        } else {
            darkModeBox.setSelected(false);
        }

        //初始化Language的值
        String language = CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId,1, 10);
        languageBox.getItems().add("简体中文");
        languageBox.getItems().add("English");
        languageBox.setValue(language);

        //初始化timezone的值
        String timezone = CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId,1, 11);
        timezoneBox.getItems().add("GMT+8 Beijing");
        timezoneBox.getItems().add("GMT+0 London");
        timezoneBox.setValue(timezone);

        // 添加Language值变化事件监听器
        languageBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // 在这里执行保存新值的操作
            CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,String.valueOf(newValue), 10, userId, 1);
        });

        // 添加timezone值变化事件监听器
        timezoneBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // 在这里执行保存新值的操作
            CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,String.valueOf(newValue), 11, userId, 1);
        });

        // 初始化linked account list

        // 如果是child account, 隐藏所有的link account相关的页面元素
        if (role.equals("child") || role.isEmpty()){
            linkTitle.setVisible(false);
            line.setVisible(false); line2.setVisible(false);
            accountField.setVisible(false);
            linkCodeField.setVisible(false);
            linkButton.setVisible(false);
            note.setVisible(false);
            linkSubTitle.setVisible(false); linkStatus.setVisible(false); linkActions.setVisible(false);
            return;
        }

        // 对于parent account, 显示link account list
        int i=0;
        List<String> linkedAccountList = new ArrayList<>();
        linkedAccountList = CSVFileHandler.getCSVMultipleValue(FileName.accountFile,userId,8,2);
        if (linkedAccountList == null){
            promptMessage.setVisible(true);
        } else {

            // 循环的方式，列出所有的链接的child account
            for (i=1; i<=linkedAccountList.size();i++) {
                System.out.print("i: " + i);
                switch (i) {
                    case 1:
                        link1.setVisible(true); link1.setText(linkedAccountList.get(0));
                        link1Status.setVisible(true); getLinkStatus(link1.getText(),link1Status);
                        unlink1.setVisible(true); ButtonHandle.unlinkButtonAction(unlink1,link1.getText());
                        default1.setVisible(true); ButtonHandle.setDefaultAction(default1, link1.getText());

                        break;
                    case 2:
                        link2.setVisible(true); link2.setText(linkedAccountList.get(1));
                        link2Status.setVisible(true); getLinkStatus(link2.getText(),link2Status);
                        unlink2.setVisible(true); ButtonHandle.unlinkButtonAction(unlink2,link2.getText());
                        default2.setVisible(true); ButtonHandle.setDefaultAction(default2, link2.getText());
                        break;
                    case 3:
                        link3.setVisible(true); link3.setText(linkedAccountList.get(2));
                        link3Status.setVisible(true); getLinkStatus(link3.getText(),link3Status);
                        unlink3.setVisible(true); ButtonHandle.unlinkButtonAction(unlink3,link3.getText());
                        default3.setVisible(true); ButtonHandle.setDefaultAction(default3, link3.getText());
                        break;
                    case 4:
                        link4.setVisible(true); link4.setText(linkedAccountList.get(3));
                        link4Status.setVisible(true); getLinkStatus(link4.getText(),link4Status);
                        unlink4.setVisible(true); ButtonHandle.unlinkButtonAction(unlink4,link4.getText());
                        default4.setVisible(true); ButtonHandle.setDefaultAction(default4, link4.getText());
                        break;
                    case 5:
                        link5.setVisible(true); link5.setText(linkedAccountList.get(4));
                        link5Status.setVisible(true); getLinkStatus(link5.getText(),link5Status);
                        unlink5.setVisible(true); ButtonHandle.unlinkButtonAction(unlink5,link5.getText());
                        default5.setVisible(true); ButtonHandle.setDefaultAction(default5, link5.getText());
                        break;
                    default:
                        System.out.print("error");
                        break;

                }
            }
        }

    }

    @FXML
    private void darkModeAction(){
        String userId = UserInfo.getInstance().getUserId();
        String mode = CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId,1, 9);
        if (mode.equals("dark")){
            CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,"light", 9, userId, 1);
        }
        if (mode.equals("light")){
            CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,"dark", 9, userId, 1);
        }
    }

    @FXML
    private void linkButtonAction(){

        String account = accountField.getText();
        String linkcode = linkCodeField.getText();
        String fileName = FileName.accountFile;

        //  clean up the error message in the screen
        errorInfo.setVisible(false);

        // check email format
        if (StringUtil.isEmpty(account) || !StringUtil.isValidEmail(account)) {
            errorInfo.setText("Email cannot be blank, or Email format is incorrect");
            errorInfo.setStyle("-fx-text-fill: #bf1b1d;");
            errorInfo.setVisible(true);
            return;
        }

        // check 输入的account是否已经有parent了
        String accountLinkId = CSVFileHandler.getCSVSingleValue(fileName, account, 2, 8);
        if (accountLinkId != null && !accountLinkId.equals("0")){
            errorInfo.setText("The account has been linked already!");
            errorInfo.setStyle("-fx-text-fill: #bf1b1d;");
            errorInfo.setVisible(true);
            return;
        }

        // 检查不能超过5个link account
        List<String> linkedAccountList = new ArrayList<>();
        String userId = UserInfo.getInstance().getUserId();
        String linkId = UserInfo.getInstance().getLinkId();
        linkedAccountList = CSVFileHandler.getCSVMultipleValue(FileName.accountFile,userId,8,2);
        if (linkedAccountList.size() >= 5){
            errorInfo.setText("You cannot link more than 5 accounts!");
            errorInfo.setStyle("-fx-text-fill: #bf1b1d;");
            errorInfo.setVisible(true);
            return;
        }

        // check account and link code
        if (VerifyAccount.isValidLinkAccount(account, linkcode)){

            String linkedUserId = CSVFileHandler.getCSVSingleValue(fileName, account, 2, 1);
            if (linkId.equals("0")){
                CSVFileHandler.updateSingleDataToCSV(fileName,linkedUserId,8,userId,1);
            }
            CSVFileHandler.updateSingleDataToCSV(fileName,userId,8,linkedUserId,1);

            initialize();
            errorInfo.setText("Linked is successfully! ");
            errorInfo.setStyle("-fx-text-fill: #caf8a7;");
            errorInfo.setVisible(true);

        } else {
            errorInfo.setText("Linked is failed! Email or link code are invalid, or account is parent role.");
            errorInfo.setVisible(true);
            errorInfo.setStyle("-fx-text-fill: #bf1b1d;");
            return;
        }

    }

    @FXML
    public void linkMouseEntered() throws IOException {
        ButtonHandle.mouseEntered(linkButton);
    }

    @FXML
    public void linkMouseExited() throws IOException {
        ButtonHandle.mouseExited(linkButton);
    }

    private static void getLinkStatus(String accountName, Label label ){

        //对于parent account, 他在文件里存储的linkID就是他默认的child account
        String userId = UserInfo.getInstance().getUserId();
        String defaultChildID = CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId, 1, 8);
        String defaultAccount = CSVFileHandler.getCSVSingleValue(FileName.accountFile, defaultChildID, 1, 2);

        if (accountName.equals(defaultAccount) ) {
            label.setText("Linked (default)");
        } else {
            label.setText("Linked");
        }

    }
}
