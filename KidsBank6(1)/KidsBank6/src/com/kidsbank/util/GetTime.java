package com.kidsbank.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This until class is to handle the time related, such as get the time, compare the time, convert time with specific format.
 */

public class GetTime {

    public static String getSystemTime(){

        // get system time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(formatter);
    }

    public static String getFutureTime(String year){
        // get system time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        LocalDateTime currentTime = LocalDateTime.now();

        if (year.equals("0.5")) {
            LocalDateTime futureTime = currentTime.plusMonths(6);
            return futureTime.format(formatter);
        } else {
            LocalDateTime futureTime = currentTime.plusYears(Long.parseLong(year));
            return futureTime.format(formatter);
        }
    }

    public static boolean compareWithCurrentTime(String time){
        // get system time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime otherTime = LocalDateTime.parse(time, formatter);
        int comparisonResult = currentTime.compareTo(otherTime);

        if (comparisonResult <= 0) {
            return false; // 表示当前时间在指定时间之前
        } else  {
            return true; // 表示当前时间在指定时间之后
        }
    }

    // 用于判断是否在指定时间段内，是则返回true, 否则返回false. 比如想检查数据建立日期，是否在最近的12个月内:compareWithSpecificTime(creationTime, 12)
    public static boolean compareWithSpecificTime(String specificTime, int months ){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        LocalDateTime specificDate2 = LocalDateTime.parse(specificTime, formatter);

        // 判断数据是否在最近N个月内建立
        if (specificDate2.isAfter(currentDate.minusMonths(months).atStartOfDay())) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertTimeFormat(String oldFormatDate){

        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");

        LocalDateTime dateTime = LocalDateTime.parse(oldFormatDate, originalFormatter);

        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return dateTime.format(targetFormatter);

    }



}
