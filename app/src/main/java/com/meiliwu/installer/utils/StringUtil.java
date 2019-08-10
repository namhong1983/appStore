package com.meiliwu.installer.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author Chaney
 * Date   2017/3/10 上午10:25.
 * EMail  qiuzhenhuan.hi@gmail.com
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("") || str.equals("null") || str.length() == 0;
    }

    public static boolean isPhoneNumber(String phone) {
        return phone.matches("^[0-9]{11}$");
    }

    public static boolean isCaptcha(String captcha) {
        return captcha.matches("^[0-9]{4}$");
    }

    public static boolean isValidPassword(String password) {

        return password.matches("^\\S{6,16}$");
    }

    public static boolean isEmail(String email) {
        return email.matches("^([0-9A-Za-z\\-_\\.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$");
    }

    public static CharSequence parseCity(String locCity) {
        if (locCity.endsWith("市")) {
            locCity = locCity.replace("市", "");
        }
        return locCity;
    }

    public static String hideCompleteNumber(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7, 11);
    }

    public static String getEmptyStrOrData(String dateStr) {
        return isEmpty(dateStr) ? "" : dateStr;
    }

    public static String convertNumToCapitalNum(int deposit_month) {
        String numStr = deposit_month + "";
        System.out.println(numStr);
        int length = numStr.toCharArray().length;
        StringBuilder builder = new StringBuilder();
        String lastNum = "";
        for (int index = 0; index < length; index++) {
            char num = numStr.charAt(index);
            switch (num) {
                case '1':
                    if (length - index == 6) {
                        break;
                    }
                    builder.append("一");
                    lastNum = "一";
                    break;
                case '2':
                    builder.append("二");
                    lastNum = "二";
                    break;
                case '3':
                    builder.append("三");
                    lastNum = "三";
                    break;
                case '4':
                    builder.append("四");
                    lastNum = "四";
                    break;
                case '5':
                    builder.append("五");
                    lastNum = "五";
                    break;
                case '6':
                    builder.append("六");
                    lastNum = "六";
                    break;
                case '7':
                    builder.append("七");
                    lastNum = "七";
                    break;
                case '8':
                    builder.append("八");
                    lastNum = "八";
                    break;
                case '9':
                    builder.append("九");
                    lastNum = "九";
                    break;
                default:
                    if (lastNum.equals("零")) {
                        break;
                    }
                    builder.append("零");
                    lastNum = "零";
                    break;
            }
            if (num == '0') {
                continue;
            }
            switch (length - index) {
                case 2:
                    builder.append("十");
                    break;
                case 3:
                    builder.append("百");
                    break;
                case 4:
                    builder.append("千");
                    break;
            }

        }
        return builder.toString();
    }

    public static boolean isEmptyNumber(String number) {
        if (number == null || number.equals("") || number.equals("0") || number.equals("null"))
            return true;
        return false;
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
