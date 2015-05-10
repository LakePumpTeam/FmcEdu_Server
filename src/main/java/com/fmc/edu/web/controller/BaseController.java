package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.util.pagenation.Pagination;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {
	private static int BASS64_MIN_LENGTH = 4;

	protected int getStatusMapping(boolean pSuccess) {
		return pSuccess ? GlobalConstant.STATUS_SUCCESS : GlobalConstant.STATUS_ERROR;
	}

	protected String generateJsonOutput(boolean pSuccess, Object pJsonData, String pMessage) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(JSONOutputConstant.PARAM_STATUS, getStatusMapping(pSuccess));
		if (pJsonData == null) {
			pJsonData = StringUtils.EMPTY;
		}
		result.put(JSONOutputConstant.PARAM_DATA, pJsonData);
		if (pMessage == null) {
			pMessage = StringUtils.EMPTY;
		}
		result.put(JSONOutputConstant.PARAM_MESSAGE, pMessage);
		return encodeOutput(JSONObject.fromObject(result).toString());
	}

	protected String encodeOutput(final String pMessage) {
		if (pMessage == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(pMessage.getBytes(Charset.forName(GlobalConstant.CHARSET_UTF8)));
	}

	protected String decodeInput(final String pParameter) throws IOException {
		if (StringUtils.isBlank(pParameter)) {
			return null;
		}
		String parameter = pParameter;
		//as the min length of base64 encoded string is 4, so we need append "=" to right if the length less then 4.
		int minLengthDiff = BASS64_MIN_LENGTH - pParameter.length();
		if(minLengthDiff > 0){
			parameter = StringUtils.rightPad(pParameter, BASS64_MIN_LENGTH, '=');
		}
		if(WebConfig.getEncodeBase64InputParam()){
			return new String((new BASE64Decoder()).decodeBuffer(parameter), Charset.forName(GlobalConstant.CHARSET_UTF8));
		}
		return pParameter;
	}


	protected Pagination buildPagination(HttpServletRequest pRequest) throws IOException {
		String pageIndex = decodeInput(pRequest.getParameter("pageIndex"));
		String pageSize = decodeInput(pRequest.getParameter("pageSize"));
		return new Pagination(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
	}
}
