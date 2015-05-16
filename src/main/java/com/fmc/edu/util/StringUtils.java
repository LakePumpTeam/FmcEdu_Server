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
}
