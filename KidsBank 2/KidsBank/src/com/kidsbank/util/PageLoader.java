package com.kidsbank.util;

import com.kidsbank.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class PageLoader {
    private Pane view;

    // 把指定的FXML文件装载到Pane里
    public Pane getPage(String fxmlFile){
        try {
            URL fileUrl = Main.class.getResource("view/" + fxmlFile);

            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file cannot be found");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return view;
    }

    public static void gotoPage(String viewName, Button button){

        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage(viewName);

        Scene currentScene = button.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载account page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }
        }

    }

}
