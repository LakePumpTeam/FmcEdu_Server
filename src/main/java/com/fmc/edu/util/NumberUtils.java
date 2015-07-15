package com.fmc.edu.util;

/**
 * Created by Yu on 2015/5/16.
 */
public class NumberUtils {
    public static final String[] NUMBER = {};// {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    public static final String[] UNIT = {};//new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};

    public static String numberToChineseNumber(int number) {
        String s = String.valueOf(number);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb = sb.append(NUMBER[Integer.parseInt(index)]);
        }
        String sss = String.valueOf(sb);
        int i = 0;
        for (int j = sss.length(); j > 0; j--) {
            sb = sb.insert(j, UNIT[i++]);
        }
        return sb.toString();
    }
}
