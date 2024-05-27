package com.kidsbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        changeView("view/login.fxml","Kids Bank - Login");

    }

    // 用跳转到一个新的窗口，原窗口会关闭
    public static void changeView (String fxmlFile, String title ){
        try {
            // 加载FXML文件
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent root = loader.load();

            // 创建新场景
            Scene scene = new Scene(root);

            // 设置舞台的场景
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 用于打开一个新的窗口，但是浮于原窗口之上
    public static void opnNewView (String fxmlFile, String title ){
        try {
            Stage newStage = new Stage();

            // 加载FXML文件
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent root = loader.load();

            // 创建新场景
            Scene scene = new Scene(root);

            // 设置舞台的场景
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);  // 设置模态性，使新窗口在当前窗口上层弹出
            newStage.setTitle(title);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Close a stage
    public static void closeStage(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }


}