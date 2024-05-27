package com.kidsbank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetTime {

    public static String getSystemTime(){

        // get system time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(formatter);
    }


}
