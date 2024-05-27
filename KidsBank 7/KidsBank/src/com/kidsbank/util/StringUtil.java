package com.kidsbank.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This until class is to handle common String check
 */

public class StringUtil {

    /**
     * Check if 2 strings are same
     * @param text1 string
     * @param text2 another string
     */
    //检查指定的2个String是否相同
    public static boolean isSame(String text1, String text2){
        return text1.equals(text2);
    }

    /**
     * Check if specific string is empty
     * @param text string
     */
    //检查指定的String是否为空
    public static boolean isEmpty(String text){
        return text == null || text.trim().equals("");
    }

    /**
     * Check if specific strings is valid email fromat
     * @param email string
     */
    //检查指定的String是否为合规的email address
    public static boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // 邮件地址的正则表达式模式

        Pattern pattern = Pattern.compile(emailRegex); // 创建Pattern对象

        Matcher matcher = pattern.matcher(email); // 使用Pattern对象创建Matcher对象

        return matcher.matches();  // 返回匹配结果
    }

    /**
     * Check if specific string is numeric
     * @param str string
     */
    // 判断一个字符串是否是数字
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // 尝试将字符串解析为 double 类型
            return true; // 如果解析成功，则返回 true
        } catch (NumberFormatException e) {
            return false; // 如果解析失败，则返回 false
        }
    }

}
