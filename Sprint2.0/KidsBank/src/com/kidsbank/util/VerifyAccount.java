package com.kidsbank.util;

import com.kidsbank.entity.FileName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VerifyAccount {

    static String csvFile = FileName.accountFile;

    //验证user email address是否已经存在
    public static boolean notExist (String username) {

        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(csvSplitBy);
                if (credentials.length >= 2 && credentials[1].equals(username) ) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    //检查user email & password是否正确
    public static boolean isValidAccount (String username, String password) {

        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(csvSplitBy);
                if (credentials.length >= 2 && credentials[1].equals(username) && credentials[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidLinkAccount(String username, String linkcode) {

        String role = CSVFileHandler.getCSVSingleValue(FileName.accountFile, username, 2, 6);
        if (role == null || role.equals("parent")) {
            return false;
        }

        if (!notExist(username)) {
            String linkcode2 = CSVFileHandler.getCSVSingleValue(FileName.accountFile, username, 2, 14);
            if (linkcode.equals(linkcode2)){
                return true;
            }
        }
    return false;
    }

}
