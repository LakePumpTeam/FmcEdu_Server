package com.fmc.edu.util;

import com.fmc.edu.model.BaseBean;

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
}
