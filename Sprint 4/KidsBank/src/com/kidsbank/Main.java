package com.kidsbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is Main class of the KidsBank application. <br>
 * &nbsp;
 * KidsBank application is developed by JavaFX <br>
 *    1) JavaFX version: javafx-sdk-21.0.2 <br>
 *    2) OpenJDK version: jdk-20.0.2 <br>
 * &nbsp;
 * KidsBank application provided the below main functions: <br>
 *    1) Sign up account (parent role, child role) <br>
 *    2) Parent account can manage the child account, such as: <br>
 *      set up the tasks, gifts, saving goals, fixed deposit interest, link to child account, etc. <br>
 *    3) Child account can implement the tasks to earn the virtual money, <br>
 *      save the money as fixed deposit to earn the interest, <br>
 *      redeem the gifts using earned virtual money. <br>
 *    4) Provides the dashboard to summarize the account balance, incomes, and expense. <br>
 *    5) Provides some basic application functions, such as Profile, Settings, Logout. <br>
 */

public class Main extends Application {

    private static Stage stage;

    /**
     * The Main method to start the KidsBank application
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * This method is called when the KidsBank application starts.
     * It initializes the JavaFX user interface.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        changeView("view/login.fxml","Kids Bank - Login");

    }

    /**
     * Switch to new view by creating new stage defined in the fxmlFile. Old view is closed
     *
     * @param fxmlFile: fxmlFile's name with the path for new view
     * @param title: new view's title
     */

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

    /**
     * Open new view by creating new stage defined in the fxmlFile. New view is in the front of old view.
     *
     * @param fxmlFile: fxmlFile's name with the path for new view
     * @param title: new view's title
     */

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

}