package com.kidsbank.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalculateBalance {

    // 计算Balance
    public static double getBalance(String csvFile, String userId) {
        double balance = 0;
        balance = getIncome(csvFile,3, userId) - getExpense(csvFile, 3, userId);
        return balance;
    }

    // 计算收入
    public static double getIncome (String csvFile, int column, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据

                if ((columns.length > column ) && (columns[column-2].equals("positive") && (columns[column+1].equals(userId)))) {
                    try {
                        double value = Double.parseDouble(columns[column-1].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid value in column " + column + ": " + columns[column-1]);
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
    public static double getExpense (String csvFile, int column, String userId) {
        List<String> columnData = new ArrayList<>();
        double sum =0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // 使用逗号分隔每一行的数据
                if ((columns.length > column ) && (columns[column-2].equals("negative")) && (columns[column+1].equals(userId))) {
                    // columnData.add(columns[column-1].trim()); // 将指定列的数据添加到列表中
                    try {
                        double value = Double.parseDouble(columns[column-1].trim()); // 将列的值转换为 double 类型
                        sum += value; // 累加列的值
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid value in column " + column + ": " + columns[column-1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return sum;
    }
}
