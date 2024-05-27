package com.kidsbank.util;

import com.kidsbank.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * This until class is to get the view or load the view in the right area of main window.
 */

public class PageLoader {
    private Pane view;

    /**
     * Load specific fxml to the pane
     * @param fxmlFile specific fxml file
     */
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

    /**
     * Switch to specific pane(view)
     * @param viewName specific page
     * @param button buttonID
     */
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
