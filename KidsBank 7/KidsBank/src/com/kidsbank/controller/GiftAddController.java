package com.kidsbank.controller;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.ButtonHandle;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.entity.FileName;
import com.kidsbank.util.GetTime;
import com.kidsbank.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This is the controller class for gift_add.fxml ("Redeem Gifts" -> "Add a gift" view）
 */

public class GiftAddController {

    @FXML
    private Button uploadGiftButton, cancelGiftButton, okGiftButton;

    @FXML
    private TextField giftValueField, giftNameField;

    @FXML
    private Label errorGiftValue;

    @FXML
    private ImageView uploadedImage;

    /**
     * Show the user's data and default UI in "Add a gift" pop-up window based on user role
     */
    @FXML
    private void initialize(){
        ButtonHandle.mouseEntered(uploadGiftButton);
        ButtonHandle.mouseExited(uploadGiftButton);
        ButtonHandle.mouseEntered(okGiftButton);
        ButtonHandle.mouseExited(okGiftButton);
        ButtonHandle.mouseEntered(cancelGiftButton);
        ButtonHandle.mouseExited(cancelGiftButton);
    }


    /**
     * UI action for adding a new gift in "Redeem Gifts" page
     */
    @FXML
    private void uploadGiftAction(){

        // 创建文件选择器
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add a gift - Select a picture file for the gift");

        // 设置文件选择器的文件类型过滤器
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Image File","*.jpg","*.png"));

        // 获得当前的stage, 显示文件选择器，并获取用户选择的文件
        File selectFile = new File("");
        Scene currentScene = uploadGiftButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                selectFile = fileChooser.showOpenDialog(stage);
            }
        }

        // 如果用户选择了文件，指定保存文件路径, 并显示出来
        if (selectFile != null) {

            String uploadFileName = FileName.defaultUploadDir + getUserId() +"_"+getGiftId() + "_gift.png";
            File saveFile = new File(uploadFileName);

            // 将用户选择的文件复制到保存路径
            try {
                Files.copy(selectFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e){
                e.printStackTrace();
            }

            //显示上传图片
            Image image = new Image("file://" +uploadFileName);
            uploadedImage.setImage(image);

        }

    }

    /**
     * UI action for confirming to add a new gift in "Redeem Gifts" page
     */
    @FXML
    private void okAddGiftAction(){

        //判断是否上传了文件，giftName是否是空，gift Value是否是数字

        if (StringUtil.isNumeric(giftValueField.getText()) &&
                uploadedImage.getImage() != null &&
                !StringUtil.isEmpty(giftValueField.getText()) &&
                !StringUtil.isEmpty(giftNameField.getText())) {

            // 把新加的Gift数据写入文件
            String[] data ;
            data = new String[]{
                    getGiftId(),
                    giftNameField.getText(),
                    giftValueField.getText(),
                    getUserId(),"open",
                    GetTime.getSystemTime(), GetTime.getSystemTime()};
            CSVFileHandler.addDataToCSV(FileName.giftsFile, data);

            // 获得当前的stage，然后关闭
            Scene currentScene = okGiftButton.getScene();
            if (currentScene != null) {
                Stage stage = (Stage) currentScene.getWindow();
                if (stage != null) {
                    stage.close();
                }
            }

        } else {
            errorGiftValue.setVisible(true);
            return;
        }
    }

    /**
     * UI action for cancelling to add a new gift in "Redeem Gifts" page
     */
    @FXML
    private void cancelAddGiftAction(){

        // 删除上传的图片文件
        String uploadFileName = FileName.defaultUploadDir + getUserId() +"_"+getGiftId() + "_gift.png";
        File file = new File(uploadFileName);

        // 判断文件是否存在, 然后以删除
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        // 获得当前的stage，然后关闭
        Scene currentScene = cancelGiftButton.getScene();
        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                stage.close();
            }
        }

    }

    /**
     * Get child user's userID
     */
    private String getUserId(){
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }
        return userId;
    }

    /**
     * Get gift's ID
     */
    private String getGiftId(){
        // 产生一个giftID, initial value is 60001, new giftID is max giftID + 1.
        int giftId = CSVFileHandler.getCSVRowCount(FileName.giftsFile);
        if (giftId == 1) {
            giftId = 60001;
        } else {
            giftId = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(FileName.giftsFile, 1)) +1;
        }

        return String.valueOf(giftId);
    }

}
