package com.kidsbank.entity;

public class FileName {
    private static String userDir = System.getProperty("user.dir");
    public static final String defaultUploadDir = userDir + "/src/com/kidsbank/image/";

    public static final String accountFile = userDir + "/src/com/kidsbank/datafile/account.csv";
    public static final String tasksFile = userDir + "/src/com/kidsbank/datafile/tasks.csv";
    public static final String giftsFile = userDir + "/src/com/kidsbank/datafile/gifts.csv";
    public static final String depositFile = userDir + "/src/com/kidsbank/datafile/fixeddeposit.csv";
    public static final String transactionFile = userDir + "/src/com/kidsbank/datafile/transaction.csv";
    public static final String tempFile = userDir + "/src/com/kidsbank/datafile/temp.csv";
    public static final String defaultProfileFile = userDir + "/src/com/kidsbank/staticfile/gift_page.png";

}
