package com.kidsbank.util;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.GiftInfo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static com.kidsbank.util.PageLoader.gotoPage;

/**
 * This until class is to add/edit/delete/get the data from csv data files
 */

public class CSVFileHandler {

    /**
     * Given a csv file, column number, then get this column's value in last row.
     * @param filePath specific csv file
     * @param colNum specific column number
     */
    //获得csv文件最后一行记录的某列的值，这里是为了获得最后一个记录的ID值
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

    /**
     * Given a csv file, then get this file's total row count.
     * @param filePath specific csv file
     */
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

    /**
     * Given a csv file, the value of column1 is columnValue1, column2 number, then get column2's multiple value.
     * @param filePath specific csv file
     * @param columnValue specific column1's value
     * @param column1 specific column1's number
     * @param column2 specific column2's number
     */
    // 指定的csv文件，给定某列(column1)的一个值(columnValue)，获取该值对应另外一列(column2)的多个值
    public static List<String> getCSVMultipleValue(String filePath, String columnValue, int column1, int column2){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            int i=0;
            List<String> columnData = new ArrayList<>();
            while ((line=reader.readLine()) != null) {
                String[] value = line.split(",");
                if (value[column1 -1].equals(columnValue)) {
                    columnData.add(value[column2 -1]);
                }
            }
            return columnData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a csv file, the value of column1 is columnValue1, the value of column2 is columnValue2, column3 number, then get column3's multiple value.
     * @param filePath specific csv file
     * @param columnValue1 specific column1's value
     * @param column1 specific column1's number
     * @param columnValue2 specific column2's value
     * @param column2 specific column2's number
     * @param column3 specific column3's number
     */
    // 指定的csv文件，给定某列(column1)的一个值(columnValue1) 以及column2的值columnValue2，获取该值对应另外一列(column3)的多个值
    public static List<String> getCSVMultipleValue2(String filePath, String columnValue1, int column1, String columnValue2, int column2, int column3){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            int i=0;
            List<String> columnData = new ArrayList<>();
            while ((line=reader.readLine()) != null) {
                String[] value = line.split(",");
                if (value[column1 -1].equals(columnValue1) && value[column2 -1].equals(columnValue2)) {
                    columnData.add(value[column3 -1]);
                }
            }
            return columnData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a csv file, column1 value, column1 number, column2's multiple value, column2 number, column3's value in specific months, column3 number, then get column4's multiple value.
     * @param filePath specific csv file
     * @param columnValue1 specific column1's value
     * @param column1 specific column1's number
     * @param columnValue21 specific column2's value
     * @param columnValue22 specific column2's value
     * @param columnValue23 specific column2's value
     * @param column3 specific column3's number
     * @param months specific last N months
     * @param column4 specific column4's number
     */
    // 指定的csv文件，给定某列(column1)的一个值(columnValue1) 以及column2的多个值columnValue21，columnValue22, columnValue23，并且column3在duration(last N months)范围内，获取该值对应另外一列(column4)的多个值
    public static List<String> getCSVMultipleValue3(String filePath, String columnValue1, int column1, String columnValue21, String columnValue22, String columnValue23, int column2, int months, int column3, int column4){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            int i=0;
            List<String> columnData = new ArrayList<>();
            while ((line=reader.readLine()) != null) {
                String[] value = line.split(",");
                if (value[column1 -1].equals(columnValue1) && GetTime.compareWithSpecificTime(value[column3-1], months)
                        && (value[column2 -1].equals(columnValue21) ||
                        value[column2 -1].equals(columnValue22) ||value[column2 -1].equals(columnValue23))) {
                    columnData.add(value[column4 -1]);
                }
            }
            return columnData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a csv file, the value of column1 is columnValue1, column2 number, then get column2's single value.
     * @param filePath specific csv file
     * @param columnValue specific column1's value
     * @param column1 specific column1's number
     * @param column2 specific column2's number
     */
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

    /**
     * Given a csv file, the value of column1 is columnValue1, the value of column2 is columnValue2, column3 number, then get column3's single value.
     * @param filePath specific csv file
     * @param columnValue1 specific column1's value
     * @param column1 specific column1's number
     * @param columnValue2 specific column2's value
     * @param column2 specific column2's number
     * @param column3 specific column3's number
     */
    // 指定的csv文件，给定某列(column1)的一个值(columnValue1) 以及column2的值columnValue2，获取该值对应另外一列(column3)的单个值
    public static String getCSVSingleValue2(String filePath, String columnValue1, int column1, String columnValue2,int column2,int column3){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line=reader.readLine()) != null) {
                String[] value = line.split(",");
                if (value[column1 -1].equals(columnValue1) && value[column2 -1].equals(columnValue2)) {
                    return value[column3 -1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a csv file, array data, then insert the array data into the file.
     * @param filePath specific csv file
     * @param newData specific array data
     */
    // 把一行新数据增加到指定的csv文件
    public static void addDataToCSV(String filePath, String[] newData ) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true )) ){

            if (!isLastLineEmpty(filePath)) {
                writer.newLine();
            }

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


    /**
     * Given a csv file, new data value, specific column number, the value of idColumn is id, then update specific column's value with new data.
     * @param filePath specific csv file
     * @param newData specific new data
     * @param column specific column's number
     * @param id specific id
     * @param idColumn specific column number
     */
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

    /**
     * Given a csv file, new data value, specific column number, the value of column1 is columnValue1, the value of column2 is columnValue2, then update specific column's value with new data
     * @param filePath specific csv file
     * @param newData specific new data
     * @param column specific column's number
     * @param column1Value specific column1's value
     * @param column1 specific column1 number
     * @param column2Value specific column2's value
     * @param column2 specific column2 number
     */
    // 更新一个数据到指定的csv文件, 已知文件名，新数据，新数据所在的列，column1的值，column1所在的列, column2的值，column2所以的列）
    public static void updateSingleDataToCSV2(String filePath, String newData, int column, String column1Value , int column1, String column2Value, int column2) {
        StringBuilder newContent = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while ((line = reader.readLine()) != null ){
                String[] value = line.split(",");
                if (value[column1-1].equals(column1Value) && value[column2 -1].equals(column2Value)){  // 找到符合条件的行
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

    /**
     * Given a csv file, the value of idColumn is id,, then delete the row where id is in the file.
     * @param filePath specific csv file
     * @param id specific id value
     * @param idColumn specific id's column number
     */
    // 删除指定的csv文件的一行, 已知文件名，id值， id值所在的列
    public static void deleteSingleLine(String filePath, String id , int idColumn) {
        File inputFile = new File(filePath);
        File tempFile = new File(FileName.tempFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null ){
                String[] value = currentLine.split(",");
                if (!value[idColumn-1].equals(id)){  // 找到id值所在的行
                    writer.write(currentLine); // 如果不符合条件，就将该行写入临时文件
                    writer.newLine();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // 删除原始文件
        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        // 重命名临时文件
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }


    }

    /**
     * Given a csv file, the value of column1 is data1, and the value of column2 is data2, then get specific tasks list in the file.
     * @param filePath specific csv file
     * @param data1 specific column1 value
     * @param column1 specific column1 number
     * @param data2 specific column1 value
     * @param column2 specific column1 number
     */
    // 读取所有符合条件的Tasks List: column1的值为data1, column2的值为data2
    // 已知文件名，data1, column1, data2, column2
    public static List<TaskInfo> getTasksList(String filePath, String data1, int column1, String data2, int column2) {

        List<TaskInfo> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line = reader.readLine();
            int i=0;
            while (line != null){
                String[] value = line.split(",");
                if (value[column1-1].equals(data1) && (value[column2-1].equals(data2))){
                    i=i+1;
                    int taskNo = i;
                    String taskId = value[0];
                    String taskName = value[1];
                    String taskValue = value[2];
                    String taskStatus = value[4];
                    String modifiedTime = value[6];
                    Button actionButton = new Button("Done");
                    actionButton.setOnAction(event -> {

                        // 增加一个OnAction for "Done" button. 弹出确认框，Yes会把task改为Pending status
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you have completed this task already?");

                        // 添加按钮
                        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                CSVFileHandler.updateSingleDataToCSV(filePath, "Pending Approval",5,taskId, 1);
                                gotoPage("earn.fxml",actionButton);
                            }
                        });
                    });

                    TaskInfo taskList = new TaskInfo(taskNo, taskId, taskName, taskValue,taskStatus,modifiedTime,actionButton);
                    result.add(taskList);
                }
                line = reader.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Given a csv file, the value of column1 is data1, and the value of column2 is data2, then get specific gifts list in the file.
     * @param filePath specific csv file
     * @param data1 specific column1 value
     * @param column1 specific column1 number
     * @param data2 specific column1 value
     * @param column2 specific column1 number
     */
    public static List<GiftInfo> getGiftList(String filePath, String data1, int column1, String data2, int column2) {

        List<GiftInfo> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line = reader.readLine();
            int i=0;
            while (line != null){
                String[] value = line.split(",");
                if (value[column1-1].equals(data1) && (value[column2-1].equals(data2))){
                    i=i+1;
                    int giftNo = i;
                    String giftId = value[0];
                    String giftName = value[1];
                    String giftValue = value[2];
                    String giftStatus = value[4];
                    String modifiedTime = value[6];

                    GiftInfo giftList = new GiftInfo(giftNo, giftId, giftName, giftValue,giftStatus,modifiedTime);
                    result.add(giftList);
                }
                line = reader.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Check whether specific file's last row is empty or not
     * @param filePath specific csv file
     */
    public static boolean isLastLineEmpty(String filePath) {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long length = file.length();
            if (length == 0) {
                return false;
            } else {
                file.seek(length - 1);
                byte lastByte = file.readByte();
                return lastByte == '\n' || lastByte == '\r'; // 判断最后一个字节是否是换行符
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }




}
