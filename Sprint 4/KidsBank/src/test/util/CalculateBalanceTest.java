package test.util;

import com.kidsbank.entity.FileName;
import com.kidsbank.util.CalculateBalance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateBalanceTest {

    String fileName = FileName.transactionFile;
    double income = 140;
    String childUserID = "332";
    String parentUserID = "331";
    double expense = 20;
    double interest = 0;
    double deposit = 60;

    @Test
    public void testGetIncome(){
        assertEquals(CalculateBalance.getIncome(fileName, childUserID),income);
    }

    @Test
    public void testGetExpense(){
        assertEquals(CalculateBalance.getExpense(fileName, childUserID),expense);
    }

    @Test
    public void testGetInterest(){
        assertEquals(CalculateBalance.getInterest(fileName, childUserID),interest);
    }

    @Test
    public void testGetDeposit(){
        assertEquals(CalculateBalance.getFixedDeposit(fileName, childUserID),deposit);
    }


}
