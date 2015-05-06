package com.fmc.edu.web.controller;

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
		return new String((new BASE64Decoder()).decodeBuffer(pParameter), Charset.forName(GlobalConstant.CHARSET_UTF8));
	}


	protected Pagination buildPagination(HttpServletRequest pRequest) throws IOException {
		String pageNum = decodeInput(pRequest.getParameter("pagecount"));
		String pageSize = decodeInput(pRequest.getParameter("pagesize"));
		return new Pagination(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
	}
}
