package com.kidsbank.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 这个类，主要就是从transaction.csv文件里读取数据。所以必须先了解向transaction.csv文件增加记录的规则。
/*
下面是transaction.csv文件，增加和修改记录的规则：

1）每当用户发生一笔对钱的增减操作时，就会向transaction.csv文件增加1条或者多条记录
2）每次增加，都会产生一个唯一的EventID，对这个“增加”的动作进行标识。
3）每次都会记录UserID, createTime, modifiedTime

============ Earn Money (Tasks) ============
当一个task被完成了以后，会向transaction.csv文件插入2条记录，sample:
  event	            type	    eventvalue	  eventID	    userID	   createtime	    modifiedtime
Implement_Tasks	    positive	300	           900001	     321	   2024/2/6 16:40	2024/2/6 16:40
Implement_Tasks_ID	id	        50001	       900001	     321	   2024/2/6 16:40	2024/2/6 16:40

("positive" 代表是正向收入，后面计算收入时，只查询“positive“对应的钱款（eventvalue)

============  Redeem Gifts (Gifts) ============
当确定兑换礼物后，会向transaction.csv文件插入2条记录，sample:
 event	            type	    eventvalue	  eventID	    userID	   createtime	    modifiedtime
Redeem_Gifts	    negative	50	          900002	    321	       2024/3/6 16:40	2024/3/6 16:40
Redeem_Gifts_ID	    id	        60001	      900002	    321	       2024/3/6 16:40	2024/3/6 16:40

("negative" 代表是支出，后面计算支出花费时，只查询“negative“对应的钱款（eventvalue)

============  Fixed Deposit ============
当存一笔钱为定期存款时，会向transaction.csv文件插入5条记录，sample:
 event	                type	eventvalue	    eventID	    userID	   createtime	    modifiedtime
FixedDeposit_Value	    open	200	            900003	     321	   2022/3/2 16:40	2024/3/6 16:40
FixedDeposit_ID	        id	    70002	        900003	     321	   2024/2/6 16:40	2024/2/6 16:40
FixedDeposit_Duration	year	2	            900003	     321	   2024/2/6 16:40	2024/2/6 16:40
FixedDeposit_StartTime	time	2023/3/2 16:40	900003	     321	   2024/2/6 16:40	2024/2/6 16:40
FixedDeposit_EndTime	time	2025/3/2 16:40	900003	     321	   2024/2/6 16:40	2024/2/6 16:40

("open" 代表这笔钱当前是定期存款，没有到期或者取消，后面计算当前存款值时，只查询“open“对应的钱款（eventvalue)
(当存款到期，或者提前withdraw的时候，会向transaction.csv里，将type “Open" 更新为"Close")

当发生一笔利息到账时，会向transaction.csv里增加一条记录，sample:
 event	                type	    eventvalue	    eventID	    userID	   createtime	    modifiedtime
 FixedDeposit_Interest	positive	30	            900003	    321	       2024/2/6 16:40	2024/2/6 16:40

============

 */


public class CalculateBalance {

    // 计算Balance
    public static double getBalance(String csvFile, String userId) {
        double balance = 0;
        balance = getIncome(csvFile, userId) - getExpense(csvFile,  userId);
        return balance;
    }

    // 计算收入

    public static double getIncome (String csvFile, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据

                if ( (columns[1].equals("positive") && (columns[4].equals(userId)))) {
                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid value in column(income): " + columns[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum;
    }

    //计算支出
    public static double getExpense (String csvFile, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据
                if ( (columns[1].equals("negative")) && (columns[4].equals(userId))) {

                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值
                    } catch (NumberFormatException e) {

                       System.err.println("Invalid value in column "  + columns[2]);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum;
    }

    //计算利息收益
    public static double getInterest (String csvFile, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据
                if ( (columns[0].equals("FixedDeposit_Interest")) && (columns[1].equals("positive")) && (columns[4].equals(userId))) {

                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值
                    } catch (NumberFormatException e) {

                        System.err.println("Invalid value in column "  + columns[2]);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum;
    }

    // 计算当前的存款额. 在Transaction文件里，只有当EventType="FixedDeposit_Value", 同时 type="Open", 才说明这个是当前的存款
    public static double getFixedDeposit (String csvFile, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据
                if ( (columns[0].equals("FixedDeposit_Value")) && (columns[1].equals("open")) && (columns[4].equals(userId))) {

                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值

                    } catch (NumberFormatException e) {

                        System.err.println("Invalid value in column "  + columns[2]);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum;
    }

    // 计算一个月内的Balance变化
    public static double getBalanceForOneMonthAgo (String csvFile, String userId) {
        List<String> columnData = new ArrayList<>();
        double sumIncome =0;
        double sumExpense =0;
        double sum=0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据

                String timeString = columns[6];
                if ((timeString == null) || (timeString.equals("modifiedtime"))){
                    continue;
                }
                // 解析时间属性
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
                LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);

                // 检查时间是否在最近一个月内。如果是一个月以内，就调出当前循环，进入下一个循环
                LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

                if (dateTime.isAfter(oneMonthAgo)) {
                    continue;
                }

                //计算收益
                if ( (columns[1].equals("positive") && (columns[4].equals(userId)))) {
                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sumIncome += value; // 累加列的值

                    } catch (NumberFormatException e) {
                        System.err.println("Invalid value in column(income): " + columns[2]);
                    }
                }

                //计算支出
                if ( (columns[1].equals("negative")) && (columns[4].equals(userId))) {
                    // columnData.add(columns[column-1].trim()); // 将指定列的数据添加到列表中
                    try {
                        double value = Double.parseDouble(columns[2].trim()); // 将列的值转换为 double 类型
                        sumExpense += value; // 累加列的值

                    } catch (NumberFormatException e) {

                        System.err.println("Invalid value in column "  + columns[2]);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum=sumIncome-sumExpense;
    }
}
