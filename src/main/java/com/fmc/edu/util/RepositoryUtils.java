package com.fmc.edu.util;

import com.fmc.edu.model.BaseBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Yove on 5/4/2015.
 */
public class RepositoryUtils {

	private static final Logger LOG = org.apache.log4j.Logger.getLogger(RepositoryUtils.class);

	public static boolean isItemExist(Object pObject) {
		if (pObject instanceof BaseBean) {
			BaseBean baseBeanObj = (BaseBean) pObject;
			return baseBeanObj.getId() != 0;
		}
		return pObject != null;
	}

	public static boolean idIsValid(final int pId) {
		return pId > 0;
	}

	public static boolean idIsValid(final String pId) {
		return idIsValid(safeParseId(pId));
	}

	public static int safeParseId(String pIdString) {
		int id = -1;
		if (StringUtils.isBlank(pIdString)) {
			return id;
		}
		try {
			id = Integer.valueOf(pIdString);
		} catch (NumberFormatException nfe) {
			StackTraceElement[] elements = nfe.getStackTrace();
			LOG.error(MessageFormat.format("{0}#{1} (line: {2})", elements[1].getClassName(), elements[1].getMethodName(), elements[1].getLineNumber()));
		}
		return id;
	}

	public static boolean isLastPageFlag(List<?> pResult, final int pPageSize) {
		if (pResult == null) {
			return true;
		}
		return (CollectionUtils.isEmpty(pResult) || pResult.size() < pPageSize);
	}
}
