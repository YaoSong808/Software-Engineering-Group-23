package com.kidsbank.controller;

import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This is the controller class for no_child.fxml (show the view when parent account does not link to any child account）
 */

public class NoChildController {

    @FXML
    private Button linkAccountButton;

    @FXML
    private void gotoSettingAction(){

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("setting.fxml");

        Scene currentScene = linkAccountButton.getScene();
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
    private void linkMouseEntered(){
        ButtonHandle.mouseEntered(linkAccountButton);
    }

    @FXML
    private void linkMouseExited(){
        ButtonHandle.mouseExited(linkAccountButton);
    }



}
