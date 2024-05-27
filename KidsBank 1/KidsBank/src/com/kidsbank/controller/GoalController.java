package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.PageLoader;
import com.kidsbank.util.StringUtil;
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
        goalValue.setText(CSVFileHandler.getCSVSingleValue(FileName.accountFile, UserInfo.getInstance().getUserId(), 1, 7));
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
       if (StringUtil.isNumeric(goalValue.getText())) {

           // 存储新的Goal值
           CSVFileHandler.updateSingleDataToCSV(FileName.accountFile, goalValue.getText(), 7, UserInfo.getInstance().getUserId(), 1);

           // 关闭窗口
           Scene currentScene = okGoalUpdateButton.getScene();
           if (currentScene != null) {
               Stage stage = (Stage) currentScene.getWindow();
               if (stage != null) {
                   stage.close();
               }
           }

           // 返回主窗口并刷新数据 (这段可以优化为公共方法，因为有重复使用）

           try {
               Stage newStage = new Stage();
               newStage.setTitle("Kids Bank");

               // 加载FXML文件
               FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/menu.fxml"));
               Parent root = loader.load();

               // 创建新场景
               Scene scene = new Scene(root);

               PageLoader pageloader = new PageLoader();
               Pane view = pageloader.getPage("home.fxml");

               BorderPane borderPane = (BorderPane) scene.lookup("#mainPane");
               if (borderPane != null) {
                   borderPane.setCenter(view);
               }

               // 设置舞台的场景
               newStage.setScene(scene);
               newStage.show();

           } catch (IOException e) {
               e.printStackTrace();
           }

       } else {
           errorGoalValue.setVisible(true);
       }


    }




}
