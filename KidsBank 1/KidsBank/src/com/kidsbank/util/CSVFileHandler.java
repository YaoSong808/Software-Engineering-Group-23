package com.kidsbank.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFileHandler {

    //获得csv文件最后一行记录的某列的值，这里是为了获得最后一个记录的userID值
    public static String getLastRowColumnValue(String filePath, int colNum) {
        String columnValue = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(","); // 假设 CSV 文件中的值以逗号分隔
                if (values.length >= colNum) {
                    columnValue = values[colNum - 1]; // 获取指定列的值（列数从1开始）
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return columnValue;
    }

    // 获得一个csv文件的行数
    public static int getCSVRowCount (String filePath) {
        int rowCount =0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)) ) {
            while (reader.readLine() != null ){
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    // 指定的csv文件，给定某列(column1)的一个值(columnValue)，获取该值所在行另外一列(column2)的值
    public static String getCSVSingleValue(String filePath, String columnValue, int column1, int column2){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line=reader.readLine()) != null) {
                String[] value = line.split(",");
                if (value[column1 -1].equals(columnValue)) {
                    return value[column2 -1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 把一行新数据增加到指定的csv文件
    public static void addDataToCSV(String filePath, String[] newData ) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true )) ){

            writer.newLine();

            for (int i=0; i < newData.length; i++){
                writer.write(newData[i]);
                if (i < newData.length - 1) {
                    writer.write (",");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    // 更新一个数据到指定的csv文件, 已知文件名，新数据，新数据所在的列，新数据对应的id, id所在的列
    public static void updateSingleDataToCSV(String filePath, String newData, int column, String id , int idColumn) {
        StringBuilder newContent = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while ((line = reader.readLine()) != null ){
                String[] value = line.split(",");
                if (value[idColumn-1].equals(id)){  // 找到id值所在的行
                   value[column-1] = newData;   // 替换指定列的值为新值
                }
                line = String.join (",",value); // 拼接更新后的行数据
                newContent.append(line).append(System.lineSeparator());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(newContent.toString()); // 将更新后的内容写回到文件中

        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
