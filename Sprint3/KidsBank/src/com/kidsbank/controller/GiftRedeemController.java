package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiftRedeemController {

@FXML
    private Label myBalance;

@FXML
    private AnchorPane giftSubPane;

@FXML
    private ScrollPane scrollPane;

@FXML
    private void initialize() throws IOException{

        // 根据用户角色，如果是parent角色，当前页面显示的都是默认child account的数据
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
            displayParentGiftList();
        } else {
            displayChildGiftList();
        }

    }

    private void displayChildGiftList() throws IOException{
        String userId=getUserId();

        // 计算my Balance的值，并显示
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        double totalFixedDeposit = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
        double availableBalance = totalBalance - totalFixedDeposit;
        myBalance.setText(String.format("%.2f", availableBalance));
        ButtonHandle.labelClick(myBalance);

        // 计算有多少open的gifts, 并拿到他们的giftID
        List<String> giftList = new ArrayList<>();
        giftList = CSVFileHandler.getCSVMultipleValue2(FileName.giftsFile,userId,4, "open", 5,1);

        if (giftList != null) {
            int i=0;
            int j=0;
            int x=16; // pane位置的初始X轴的值
            int y=6;  // pane位置的初始Y轴的值

            for (i=1; i<=giftList.size();i++) {

                String giftId = giftList.get(i-1);

                // 给每个pane计算pane的位置
                if (i % 4 == 0) {
                    j = j + 1;
                    x = 16;
                    y = y + 172;
                } else {
                    x = x + 135;
                }

                showGiftPane(x, y, giftId);

            }
        }

    }

    private void displayParentGiftList() throws IOException{

        String userId=getUserId();

        // 计算my Balance的值，并显示
        double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
        double totalFixedDeposit = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
        double availableBalance = totalBalance - totalFixedDeposit;
        myBalance.setText(String.format("%.2f", availableBalance));
        ButtonHandle.labelClick(myBalance);

        // 计算有多少open的gifts, 并拿到他们的giftID
        List<String> giftList = new ArrayList<>();
        giftList = CSVFileHandler.getCSVMultipleValue2(FileName.giftsFile,userId,4, "open", 5,1);

        // 把Add a Gift的pane加载
        addGiftPane();

        if (giftList != null) {
            int i=0;
            int j=0;
            int x=151; // pane位置的初始X轴的值
            int y=6;  // pane位置的初始Y轴的值

            // 用循环的方式，给每个gift增加一个pane，用于Redeem操作
            for (i=1; i<=giftList.size();i++) {

                String giftId = giftList.get(i-1);

                // 给每个pane计算pane的位置
                if ((i+1) % 4 == 0) {
                    j = j + 1;
                    x = 16;
                    y = y + 172;
                } else {
                    x = x + 135;
                }
                showGiftPane(x, y, giftId);

            }
        }

    }

    private void addGiftPane() throws IOException {
        String userId=getUserId();

        // 显示“Add a Gift" pane, 在Pane中有一个Add button, 用户点击后可以增加一个gift
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #64A270; -fx-background-radius: 10;");
        pane.setPrefSize(127, 161);
        pane.setLayoutX(151);
        pane.setLayoutY(6);
        giftSubPane.getChildren().add(pane); //把Pane增加到ArchorPane
        Button button = new Button();
        button.setPrefSize(30, 30);
        button.setLayoutX(40);
        button.setLayoutY(62);
        button.setStyle("-fx-background-color: #64A270;");
        pane.getChildren().add(button); //把button加到Pane
        Image image = new Image("file://"+System.getProperty("user.dir") + "/src/com/kidsbank/staticfile/plus.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        button.setGraphic(imageView);
        ButtonHandle.mouseEntered(button);
        ButtonHandle.mouseExited(button);

        // 给”Add a gift" button添加On action
        button.setOnAction(event -> {
            Main.opnNewView("view/gift_add.fxml", "Kids Bank - Add a gift");
        });

        // 将一个Refresh button放到Pane里，用于让refresh the gift list
        Button refreshButton = new Button();
        refreshButton.setPrefSize(15, 15);
        refreshButton.setLayoutX(90);
        refreshButton.setLayoutY(132);
        refreshButton.setStyle("-fx-background-color: #64A270;");
        pane.getChildren().add(refreshButton); //把button加到Pane
        Image refreshImage = new Image("file://"+System.getProperty("user.dir") + "/src/com/kidsbank/staticfile/refresh.png");
        ImageView refreshImageView = new ImageView(refreshImage);
        refreshImageView.setFitWidth(15);
        refreshImageView.setFitHeight(15);
        refreshButton.setGraphic(refreshImageView);
        ButtonHandle.mouseEntered(refreshButton);
        ButtonHandle.mouseExited(refreshButton);

        // 给”refresh" button添加On action
        refreshButton.setOnAction(event -> {
            try {
                initialize();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void showGiftPane(int x, int y, String giftId){
        String userId=getUserId();

        // 增加一个pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #64A270; -fx-background-radius: 10;");
        pane.setPrefSize(127, 161);
        pane.setLayoutX(x); // 设置左上角横坐标
        pane.setLayoutY(y); // 设置左上角纵坐标

        // 将new pane 加入到AchorPane
        giftSubPane.getChildren().add(pane);

        // 检查gift图片是否存在，不存在就用默认图片代替
        String giftPic = FileName.defaultUploadDir + userId+"_"+giftId+"_gift.png";
        File file = new File (giftPic);
        if (!file.exists()) {
            giftPic = FileName.defaultGiftFile;
        }

        // 新建并设置ImageView
        Image image = new Image("file://" +giftPic);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(97); // 设置宽度限制
        imageView.setFitHeight(110); // 设置高度限制
        imageView.setLayoutX(15); // 设置左上角横坐标
        imageView.setLayoutY(10); // 设置左上角纵坐标

        // 将ImageView 添加到 Pane 中
        pane.getChildren().add(imageView);
        imageView.setImage(image);

        // 将一个显示价格的label放到Pane里，并显示
        String giftValue = CSVFileHandler.getCSVSingleValue(FileName.giftsFile, giftId, 1, 3);
        Label price = new Label();
        price.setPrefWidth(127); // 设置宽度
        price.setPrefHeight(15); // 设置高度
        price.setLayoutY(120);  //设置左上角纵坐标
        price.setStyle("-fx-font-family: System; -fx-font-size: 10px;");
        price.setAlignment(Pos.CENTER);
        price.setTextFill(Color.WHITE); // 设置文本颜色
        pane.getChildren().add(price);
        price.setText("RMB: "+giftValue+".00");

        // 将一个"Redeem" button放到Pane里，用于让user点击并确认兑换
        Button redeemButton = new Button("Redeem");
        redeemButton.setPrefSize(61, 10);
        redeemButton.setLayoutX(33);
        redeemButton.setLayoutY(135);
        redeemButton.setStyle("-fx-background-color: #307E54; -fx-background-radius: 10; -fx-font-family: System; -fx-font-size: 10px; -fx-font-weight: bold;-fx-text-fill: WHITE;");
        pane.getChildren().add(redeemButton);
        ButtonHandle.mouseEntered(redeemButton);
        ButtonHandle.mouseExited(redeemButton);

        // 给"Redeem" button增加On Action. 更新文件，将gift状态更新为"closed", 并刷新页面
        redeemButton.setOnAction(event -> {

            // 如果余额不足，提醒用户
            double totalBalance = CalculateBalance.getBalance(FileName.transactionFile, userId);
            double totalFixedDeposit = CalculateBalance.getFixedDeposit(FileName.transactionFile, userId);
            double availableBalance = totalBalance - totalFixedDeposit;
            if (Double.parseDouble(giftValue) > availableBalance) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("You balance is not enough to redeem the selected gift.");
                alert.showAndWait();
            } else {

                // 确认对话框
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to purchase this gift?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // 确认后，跟新Gift文件里，该gift的状态为close，并刷新页面, 同时向Transaction 文件里插入相关event数据
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {

                        // 更新Gift文件
                        CSVFileHandler.updateSingleDataToCSV(FileName.giftsFile, "closed", 5, giftId, 1);
                        CSVFileHandler.updateSingleDataToCSV(FileName.giftsFile, GetTime.getSystemTime(), 7, giftId, 1);

                        // generate eventID: initial value is 900001, new eventID is max userID + 1.
                        int eventID = CSVFileHandler.getCSVRowCount(FileName.transactionFile);
                        if (eventID == 1) {
                            eventID = 900001;
                        } else {
                            eventID = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(FileName.transactionFile, 4)) + 1;
                        }

                        // 向Transaction文件插入event数据：
                        // 增加2行数据到 transaction文件
                        String[] data1;
                        data1 = new String[]{"Redeem_Gifts", "negative",
                                giftValue,
                                String.valueOf(eventID),
                                userId,
                                GetTime.getSystemTime(), GetTime.getSystemTime()};
                        CSVFileHandler.addDataToCSV(FileName.transactionFile, data1);

                        String[] data2;
                        data2 = new String[]{"Redeem_Gifts_ID", "id",
                                giftId,
                                String.valueOf(eventID),
                                userId,
                                GetTime.getSystemTime(), GetTime.getSystemTime()};
                        CSVFileHandler.addDataToCSV(FileName.transactionFile, data2);
                    }
                });

                // 清除所有的gifts list的Pane
                giftSubPane.getChildren().removeIf(node -> !"firstPane".equals(node.getId()));

                // 重新初始化
                try {
                    initialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // 将一个"Delete" button放到Pane里，用于让delete the gift, 仅仅parent user可见
        if (isParent()){
            Button deleteButton = new Button();
            deleteButton.setPrefSize(10, 15);
            deleteButton.setLayoutX(95);
            deleteButton.setLayoutY(132);
            deleteButton.setStyle("-fx-background-color: #64A270;");
            pane.getChildren().add(deleteButton); //把button加到Pane
            Image deleteImage = new Image("file://"+System.getProperty("user.dir") + "/src/com/kidsbank/staticfile/trash.png");
            ImageView deleteImageView = new ImageView(deleteImage);
            deleteImageView.setFitWidth(10);
            deleteImageView.setFitHeight(15);
            deleteButton.setGraphic(deleteImageView);
            ButtonHandle.mouseEntered(deleteButton);
            ButtonHandle.mouseExited(deleteButton);

            // 给"Delete" button增加On Action. 删除上传的文件，删除gift文件里的对应记录, 删除对应的Pane
            deleteButton.setOnAction(event -> {

                // 确认对话框
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this gift?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // 确认后，执行删除
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        file.delete();
                        CSVFileHandler.deleteSingleLine(FileName.giftsFile, giftId, 1);
                    }
                });

                // 清除所有的gifts list的Pane
                giftSubPane.getChildren().removeIf(node -> !"firstPane".equals(node.getId()));

                // 重新初始化
                try {
                    initialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    private String getUserId(){
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }
        return userId;
    }

    private boolean isParent(){
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        if (role.equals("parent")) {
            return true;
        }
        return false;
    }

    @FXML
    private void gotoAccount(){
        PageLoader pageloader = new PageLoader();
        Pane view = pageloader.getPage("account.fxml");

        Scene currentScene = myBalance.getScene();
        if (currentScene != null) {

            // 找到BorderPane "mainPane", 并装载account page到右边区域
            BorderPane pane = (BorderPane) currentScene.lookup("#mainPane");
            if (pane != null) {
                pane.setCenter(view);
            }

            // 把Gift Menu的button背景色清除
            Button giftButton = (Button) currentScene.lookup("#giftButton");
            if (giftButton != null){
                giftButton.setStyle("-fx-background-color: #155434;-fx-background-radius: 60;");
            }

            // 增加Account Menu的button背景色
            Button earnButton = (Button) currentScene.lookup("#accountButton");
            if (earnButton != null){
                earnButton.setStyle("-fx-background-color: #307e54;-fx-background-radius: 60;");
            }
        }
    }

}
