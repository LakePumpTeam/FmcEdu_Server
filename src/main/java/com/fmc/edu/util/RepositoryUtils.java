package com.fmc.edu.util;

import com.fmc.edu.model.BaseBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Yove on 5/4/2015.
 */
public class RepositoryUtils {

    public static boolean isItemExist(Object pObject) {
        if (pObject instanceof BaseBean) {
            BaseBean baseBeanObj = (BaseBean) pObject;
            return baseBeanObj.getId() != 0;
        }
        return pObject != null;
    }

    public static boolean idIsValid(final int pId) {
        return idIsValid(String.valueOf(pId));
    }

    public static boolean idIsValid(final String pId) {
        if (StringUtils.isBlank(pId)) {
            return false;
        }
        if ("0".equals(pId.trim())) {
            return false;
        }
        return StringUtils.isNumeric(pId);
    }

    public static boolean isLastPageFlag(List<?> pResult, final int pPageSize) {
        if (pResult == null) {
            return true;
        }
        return (CollectionUtils.isEmpty(pResult) || pResult.size() < pPageSize);
    }
}
