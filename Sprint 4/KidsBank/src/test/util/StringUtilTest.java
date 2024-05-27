package test.util;

import com.kidsbank.util.StringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest {
    String text1 = "abc@";
    String text2 = "abc@";
    String text3 = "";

    String text4 = "abc.def.gf";

    String text5 = "abc@def.gh";

    String text6 = "908";

    @Test
    public void testSameString(){
        assertTrue(StringUtil.isSame(text1, text2));
    }

    @Test
    public void testEmptyString(){
        assertTrue(StringUtil.isEmpty(text3));
    }

    @Test
    public void testWrongEmailString(){
        assertFalse(StringUtil.isValidEmail(text4));
    }

    @Test
    public void testCorrectEmailString(){
        assertTrue(StringUtil.isValidEmail(text5));
    }

    @Test
    public void testNumericString(){
        assertTrue(StringUtil.isNumeric(text6));
    }

}
