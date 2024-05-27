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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GoalController {

    @FXML
    private TextField goalValue;

    @FXML
    private Label errorGoalValue;

    @FXML
    private Button cancelGoalUpdateButton;

    @FXML
    private Button okGoalUpdateButton;

    @FXML
    public void initialize(){

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        goalValue.setText(CSVFileHandler.getCSVSingleValue(FileName.accountFile, userId, 1, 7));
    }
    @FXML
    public void cancelGoalUpdate(){

        // 关闭窗口
        Scene currentScene = cancelGoalUpdateButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }
    }

    @FXML
    public void okGoalUpdate(){

       errorGoalValue.setVisible(false);

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

       if (StringUtil.isNumeric(goalValue.getText())) {

           // 存储新的Goal值
           CSVFileHandler.updateSingleDataToCSV(FileName.accountFile, goalValue.getText(), 7, userId, 1);

           // 更新Modified Time
           CSVFileHandler.updateSingleDataToCSV(FileName.accountFile, GetTime.getSystemTime(), 13, userId, 1);

           Scene currentScene = okGoalUpdateButton.getScene();
           if (currentScene != null) {
               Stage stage = (Stage) currentScene.getWindow();
               if (stage != null) {
                   stage.close();
               }
           }

       } else {
           errorGoalValue.setVisible(true);
       }


    }


}
