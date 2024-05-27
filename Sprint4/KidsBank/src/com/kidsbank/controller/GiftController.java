package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.CalculateBalance;
import com.kidsbank.util.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.io.IOException;
/**
 * This is the controller class for gift.fxml ("Redeem Gifts" view）
 */

public class GiftController {

    @FXML
    private Label childDataPrompt;

    @FXML
    private Button changeButton;

    @FXML
    private Pane headerPane;

    @FXML Label headerPromptLabel;

    @FXML
    private BorderPane giftPane;

    @FXML
    private Button redeemGiftButton, purchaseGiftButton;

    /**
     * Show the default UI in "Redeem Gift" page based on user role
     */
    @FXML
    private void initialize(){

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
        }

        // 在提示语句里显示用户名字
        String fileName = FileName.accountFile;
        String firstName = CSVFileHandler.getCSVSingleValue(fileName,userId,1,4);
        headerPromptLabel.setText(firstName +", explore, shop, redeem. You deserve it!");

        // Load Gift Redeem page
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("gift_redeem.fxml");
        giftPane.setCenter(view);
        redeemGiftButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

        // 增加按钮效果
        ButtonHandle.mouseEntered(redeemGiftButton);
        ButtonHandle.mouseExited(redeemGiftButton);
        ButtonHandle.mouseEntered(purchaseGiftButton);
        ButtonHandle.mouseExited(purchaseGiftButton);


    }

    /**
     * UI action to refresh the "Redeem Gifts" page
     */
    @FXML
    private void redeemGiftAction(){
        initialize();
        redeemGiftButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");
        purchaseGiftButton.setStyle("-fx-background-color: #64A270; -fx-background-radius: 60;");
    }

    /**
     * UI action for switching to "Redeem Gifts" -> "History" page
     */
    @FXML
    private void purchasedGiftAction(){
        // Load Purchased Gift page
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("gift_purchased.fxml");
        giftPane.setCenter(view);
        redeemGiftButton.setStyle("-fx-background-color: #64A270; -fx-background-radius: 60;");
        purchaseGiftButton.setStyle("-fx-background-color: #155434; -fx-background-radius: 60;");

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
            Button homeButton = (Button) currentScene.lookup("#giftButton");
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

}
