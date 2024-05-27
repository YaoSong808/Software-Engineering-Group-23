package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    private TextField displayNameField, firstNameField, lastNameField, parentField, linkCodeField;

    @FXML
    private Label parentLabel, linkLabel;

    @FXML
    private void initialize(){
        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();

        String fileName = FileName.accountFile;
        String firstName = CSVFileHandler.getCSVSingleValue(fileName,userId,1,4);
        String lastName = CSVFileHandler.getCSVSingleValue(fileName,userId,1,5);
        String parentId = CSVFileHandler.getCSVSingleValue(fileName,userId,1,8);
        String linkCode = CSVFileHandler.getCSVSingleValue(fileName,userId,1,14);
        String parentAccount = CSVFileHandler.getCSVSingleValue(fileName,parentId,1,2);

        displayNameField.setText(firstName+" "+lastName);
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



    }


}
