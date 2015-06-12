package com.fmc.edu.util;

import java.util.regex.Pattern;

/**
 * Created by Yove on 5/6/2015.
 */
public class ValidationUtils {

    protected static final String REGEX_PATTERN_PHONE_NUMBER = "^(((13[0-9])|(15([0-9]))|(17([0-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";

    protected static final Pattern PHONE_PATTERN = Pattern.compile(REGEX_PATTERN_PHONE_NUMBER);

    public static boolean isValidPhoneNumber(String pPhone) {
        if (StringUtils.isBlank(pPhone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(pPhone).matches();
    }
}
