package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.StringUtil;
import com.kidsbank.util.VerifyAccount;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SignupController {

    @FXML
    private Label errorInfo;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordAgainField;

    @FXML
    private RadioButton parentButton;

    @FXML
    private RadioButton childButton;

    @FXML
    private ToggleGroup role;

    String accountRole;

    private int userID;

    @FXML
    private void doSignup() throws IOException {
        //  clean up the error message in the screen
        errorInfo.setVisible(false);

        // check email format
        if (StringUtil.isEmpty(emailField.getText()) || !StringUtil.isValidEmail(emailField.getText())) {
            errorInfo.setText("Email cannot be blank, or Email format is incorrect");
            errorInfo.setVisible(true);
            return;
        }

        // check password consistency
        if (StringUtil.isEmpty(passwordField.getText()) || !StringUtil.isSame(passwordField.getText(), passwordAgainField.getText()) ) {
            errorInfo.setText("Password cannot be blank, or password is inconsistent");
            errorInfo.setVisible(true);
            return;
        }

        // get system time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String createTime = currentTime.format(formatter);

        // get role type
        if (childButton.isSelected()) {
                accountRole = "child";
        } else {
                accountRole = "parent";
        }

        // account file location
        String csvFile = FileName.accountFile;

        // generate userID: initial value is 321, new userID is max userID + 1.
        userID = CSVFileHandler.getCSVRowCount(csvFile);
        if (userID == 1) {
            userID = 321;
        } else {
            userID = Integer.parseInt(CSVFileHandler.getLastRowColumnValue(csvFile, 1)) +1;
        }

        // get user account input data
        String[] data ;
        data = new String[]{
                String.valueOf(userID),
                String.valueOf(emailField.getText()),
                String.valueOf(passwordField.getText()),
                String.valueOf(firstNameField.getText()),
                String.valueOf(lastNameField.getText()),
                accountRole, "0", "0","light","English","GMT+8", createTime, createTime };


        // check email exist, and sign up account if not exist
        if (VerifyAccount.notExist(emailField.getText())){
            CSVFileHandler.addDataToCSV(csvFile, data);
            Main.changeView("view/login.fxml");
        } else {
            errorInfo.setText("The email already exists, please use other email and try again!");
            errorInfo.setVisible(true);
        }

    }
}
