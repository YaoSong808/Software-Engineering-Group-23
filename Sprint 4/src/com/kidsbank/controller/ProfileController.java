package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.kidsbank.entity.FileName.defaultUploadDir;

/**
 * This is the controller class for profile.fxml ("Profile" view）
 */

public class ProfileController {

    @FXML
    private TextField firstNameField, lastNameField, parentField, linkCodeField;

    @FXML
    private Label parentLabel, linkLabel;

    @FXML
    private ImageView myProfilePicture;

    @FXML
    private Button changePictureButton;

    /**
     * Show the user's profile data and default UI in "Profile" page based on user role
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();

        String fileName = FileName.accountFile;
        String firstName = CSVFileHandler.getCSVSingleValue(fileName,userId,1,4);
        String lastName = CSVFileHandler.getCSVSingleValue(fileName,userId,1,5);
        String parentId = CSVFileHandler.getCSVSingleValue(fileName,userId,1,8);
        String linkCode = CSVFileHandler.getCSVSingleValue(fileName,userId,1,14);
        String parentAccount = CSVFileHandler.getCSVSingleValue(fileName,parentId,1,2);

        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        parentField.setText(parentAccount);
        linkCodeField.setText(linkCode);

        parentField.setDisable(true);
        linkCodeField.setDisable(true);

        if (role.equals("parent") || role.isEmpty()) {
            parentField.setVisible(false);
            linkCodeField.setVisible(false);
            parentLabel.setVisible(false);
            linkLabel.setVisible(false);
        }

        // 加载头像, 如果用户不曾上传头像，使用默认头像

        String profilePicture = FileName.defaultUploadDir + UserInfo.getInstance().getUserId()+"_profile.png";
        File file = new File (profilePicture);

        if (!file.exists()) {
            profilePicture = FileName.defaultProfileFile;
        }
        Image image = new Image("file://" +profilePicture);
        myProfilePicture.setImage(image);


       // 添加First Name 值变化事件监听器
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {

          // 在这里执行保存新值的操作
           CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,String.valueOf(newValue), 4, userId, 1);
       });

        // 添加Last Name 值变化事件监听器
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {

            // 在这里执行保存新值的操作
            CSVFileHandler.updateSingleDataToCSV(FileName.accountFile,String.valueOf(newValue), 5, userId, 1);
        });

    }

    /**
     * Update user's profile picture in "Profile" page
     */
    @FXML
    private void changePictureAction() throws IOException {

        // 创建文件选择器
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a picture file");

        // 设置文件选择器的文件类型过滤器
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Image File","*.jpg","*.png"));

        // 获得当前的stage, 显示文件选择器，并获取用户选择的文件
        File selectFile = new File("");
        Scene currentScene = changePictureButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                selectFile = fileChooser.showOpenDialog(stage);
            }
        }

        // 如果用户选择了文件，则加载并显示图片
        if (selectFile != null){

            // 指定保存文件路径
            File saveFile = new File(FileName.defaultUploadDir+ UserInfo.getInstance().getUserId()+"_profile.png");

            // 将用户选择的文件复制到保存路径
            Files.copy(selectFile.toPath(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);


            Image image = new Image(selectFile.toURI().toString());
            myProfilePicture.setImage(image);

        }





    }


}
