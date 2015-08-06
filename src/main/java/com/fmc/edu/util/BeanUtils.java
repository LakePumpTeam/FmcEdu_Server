package com.fmc.edu.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Yu on 7/18/2015.
 */
public class BeanUtils {

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if ((o instanceof String)) {
            if (((String) o).trim().length() == 0) {
                return true;
            }
        } else if ((o instanceof Collection)) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if ((o instanceof Map)) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else if ((o instanceof Long)) {
            if (o == null) {
                return true;
            }
        } else if ((o instanceof Short)) {
            if (o == null) {
                return true;
            }
        } else if ((o instanceof Integer)) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotEmpty(Long o) {
        return !isEmpty(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null) {
            return false;
        }
        if ((o instanceof Number)) {
            return true;
        }
        if ((o instanceof String)) {
            try {
                Double.parseDouble((String) o);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isValidClockInType(String pType) {
        if (!isNumber(pType)) {
            return false;
        }
        return !(!pType.equalsIgnoreCase("1") && !pType.equalsIgnoreCase("2") && !pType.equalsIgnoreCase("3") && !pType.equalsIgnoreCase("4") && !pType.equalsIgnoreCase("5"));
    }
}
