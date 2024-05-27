package com.kidsbank.util;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import static com.kidsbank.util.PageLoader.gotoPage;

/**
 * This util class is to handle Button's common actions
 */

public class ButtonHandle {

    public static void mouseEntered(Button button) {
        // 创建一个阴影效果
        DropShadow shadow = new DropShadow();

        // 设置阴影的偏移和颜色
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);
        shadow.setColor(javafx.scene.paint.Color.YELLOW);

        // 给按钮添加鼠标按下事件
        button.setOnMouseEntered(e -> {        // 设置button的mouse over效果
            button.setEffect(shadow);
            button.setTextFill(Color.YELLOW);
            button.setUnderline(true);
        });

    }

    public static void mouseExited(Button button) {
        button.setOnMouseExited(e -> {        // 设置button的mouse exit效果
            button.setEffect(null);
            button.setTextFill(Color.WHITE);
            button.setUnderline(false);
        });

    }

    public static void labelClick(Label label) {
        // 创建一个阴影效果
        DropShadow shadow = new DropShadow();

        // 设置阴影的偏移和颜色
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);
        shadow.setColor(javafx.scene.paint.Color.YELLOW);

        // 给Label添加鼠标按下事件
        label.setOnMouseEntered(e -> {        // 设置label的mouse over效果
            label.setEffect(shadow);
            label.setTextFill(Color.YELLOW);
            label.setUnderline(true);
        });

        label.setOnMouseExited(e -> {        // 设置label的mouse exit效果
            label.setEffect(null);
            label.setTextFill(Color.WHITE);
            label.setUnderline(false);
        });

    }

    public static void unlinkButtonAction(Button button, String userName){
        button.setOnAction(event -> {

            // 增加一个OnAction for the button. 弹出确认框，Yes会把account给unlink
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to unlink this child account?");

            // 添加按钮
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    CSVFileHandler.updateSingleDataToCSV(FileName.accountFile, "0", 8, userName, 2);
                    gotoPage("setting.fxml", button);
                }
            });
        });

    }

    public static void setDefaultAction(Button button, String userName){
        button.setOnAction(event -> {

            // 增加一个OnAction for the button. 弹出确认框，Yes会把account给unlink
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to set this child account as default?");

            // 添加按钮
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    String fileName = FileName.accountFile;
                    String userId = UserInfo.getInstance().getUserId();
                    String newLinkID = CSVFileHandler.getCSVSingleValue(fileName,userName,2,1);
                    CSVFileHandler.updateSingleDataToCSV(FileName.accountFile, newLinkID, 8, userId, 1);
                    UserInfo.getInstance().setLinkId(newLinkID);
                    gotoPage("setting.fxml", button);
                }
            });
        });

    }

}