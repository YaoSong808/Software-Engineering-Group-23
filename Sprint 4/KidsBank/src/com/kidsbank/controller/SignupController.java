package com.kidsbank.controller;

import com.kidsbank.Main;
import com.kidsbank.entity.FileName;
import com.kidsbank.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * This is the controller class for signup.fxml ("Signup" view）
 */

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
    private Button submitButton;

    @FXML
    private ToggleGroup role;

    String accountRole;

    private int userID;

    /**
     * UI effect for mouse action
     */
    public void signupMouseEntered(){
        ButtonHandle.mouseEntered(submitButton);
    }

    /**
     * UI effect for mouse action
     */
    public void signupMouseExited(){
        ButtonHandle.mouseExited(submitButton);
    }

    /**
     * Verify user input info and implement new account signup
     */
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

        // generate 一个随机数，用于linkcode

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        // get user account input data
        String[] data ;
        data = new String[]{
                String.valueOf(userID),
                String.valueOf(emailField.getText()),
                String.valueOf(passwordField.getText()),
                String.valueOf(firstNameField.getText()),
                String.valueOf(lastNameField.getText()),
                accountRole, "0", "0","light","English","GMT+8 Beijing",
                GetTime.getSystemTime(), GetTime.getSystemTime(), String.valueOf(randomNumber)};


        // check email exist, and sign up account successfully if not exist
        if (VerifyAccount.notExist(emailField.getText())){
            CSVFileHandler.addDataToCSV(csvFile, data);
            Main.changeView("view/login.fxml", "Kids Bank - Login");
        } else {
            errorInfo.setText("The email already exists, please use other email and try again!");
            errorInfo.setVisible(true);
        }

    }
}
