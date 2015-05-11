package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.crypto.Base64CryptoService;
import com.fmc.edu.util.pagenation.Pagination;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {
    @Resource(name = "base64CryptoService")
    private Base64CryptoService mBase64CryptoService;

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
        return mBase64CryptoService.encrypt(pMessage);
    }

    protected String decodeInput(final String pParameter) throws IOException {
        if (!WebConfig.isEncodeBase64InputParam()) {
            return pParameter;
        }
        return mBase64CryptoService.decrypt(pParameter);
    }


	protected Pagination buildPagination(HttpServletRequest pRequest) throws IOException {
		String pageIndex = decodeInput(pRequest.getParameter("pageIndex"));
		String pageSize = decodeInput(pRequest.getParameter("pageSize"));
		return new Pagination(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
	}

    public Base64CryptoService getBase64CryptoService() {
        return mBase64CryptoService;
    }

    public void setBase64CryptoService(Base64CryptoService pBase64CryptoService) {
        this.mBase64CryptoService = pBase64CryptoService;
    }
}
