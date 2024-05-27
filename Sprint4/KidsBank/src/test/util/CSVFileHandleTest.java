package test.util;

import com.kidsbank.entity.FileName;
import com.kidsbank.util.CSVFileHandler;
import com.kidsbank.util.GetTime;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVFileHandleTest {
    String file1 = FileName.depositFile;
    String file2 = FileName.transactionFile;
    String file3 = FileName.tasksFile;
    String userId = "332";

    @Test
    public void testGetLastRowValue(){
        String columnValue="70002";
        assertEquals(CSVFileHandler.getLastRowColumnValue(file1, 1), columnValue);
    }

    @Test
    public void testGetFileRowNumber(){
        int file1RowNumber = 4;
        assertEquals(CSVFileHandler.getCSVRowCount(file1), file1RowNumber);
    }

    @Test
    public void testGetMultipleValue(){
        List<String> list = Arrays.asList("40", "50051");
        String eventID = "900038";
        assertEquals(CSVFileHandler.getCSVMultipleValue(file2, eventID, 4, 3), list);
    }

    @Test
    public void testGetMultipleValue2(){
        List<String> list = Arrays.asList("40", "100");
        String userId = "332";
        assertEquals(CSVFileHandler.getCSVMultipleValue2(file2, userId, 5,"positive",2, 3), list);
    }

    @Test
    public void testGetSingleValue(){
        String value = "10";
        String id = "50048";
        assertEquals(CSVFileHandler.getCSVSingleValue(file3, id, 1, 3), value);
    }

    @Test
    public void testGetSingleValue2(){
        String value = "40";
        assertEquals(CSVFileHandler.getCSVSingleValue2(file2, userId, 5, "positive",2, 3), value);

    }

    @Test
    public void testAddUpdateDeleteData(){
        String time = GetTime.getSystemTime();
        String[] newRowData = {"80000", "80", "unit test data", "80%",time, time };
        CSVFileHandler.addDataToCSV(file1, newRowData);
        CSVFileHandler.updateSingleDataToCSV(file1, "unit test data updated",3,"80000", 1);
        CSVFileHandler.updateSingleDataToCSV2(file1, "unit test data updated2",3, "80", 2, "80%",4);
        assertEquals(CSVFileHandler.getCSVRowCount(file1), 5);
        CSVFileHandler.deleteSingleLine(file1, "unit test data updated2", 3);
        assertEquals(CSVFileHandler.getCSVRowCount(file1), 4);
    }

}
