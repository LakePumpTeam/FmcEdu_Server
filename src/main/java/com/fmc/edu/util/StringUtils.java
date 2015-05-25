package com.fmc.edu.util;

/**
 * Created by silly on 2015/5/16.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static Object ifNULLReturn(Object pStr, String pReturn) {
        if (pStr == null) {
            return pReturn;
        }
        return pStr;
    }

    public static String normalizeUrlNoEndSeparator(String url) {
        if (isBlank(url)) {
            return EMPTY;
        }
        url = url.replace("\\", "/");
        return url.replace("//", "/");
    }

    public static void main(String[] args) {
        String path = "\\test\\sdt\\ds";
        String path1 = "/sds//sds/asd//asdw//wesds";
        System.out.println(normalizeUrlNoEndSeparator(path));
        System.out.println(normalizeUrlNoEndSeparator(path1));
    }
}
