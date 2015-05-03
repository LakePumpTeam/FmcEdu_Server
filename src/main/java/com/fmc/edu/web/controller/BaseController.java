package com.fmc.edu.web.controller;

import com.fmc.edu.constant.JSONOutputConstant;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {

	protected static final String SUCCESS = "0";

	protected static final String ERROR = "-1";


	protected String getStatusMapping(boolean pSuccess) {
		return pSuccess ? SUCCESS : ERROR;
	}

	protected String generateJsonOutput(boolean pSuccess, Object pJsonData, String pMessage) {
		Map<String, Object> result = new HashMap<>();
		result.put(JSONOutputConstant.PARAM_STATUS, getStatusMapping(pSuccess));
		if (pJsonData != null) {
			result.put(JSONOutputConstant.PARAM_DATA, pJsonData);
		}
		if (StringUtils.isNoneBlank(pMessage)) {
			result.put(JSONOutputConstant.PARAM_MESSAGE, pMessage);
		}
		return JSONObject.fromObject(result).toString();
	}
}
