package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

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
        Map<String, Object> result = new HashMap<>();
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
        if (pMessage == null) return null;
        return (new BASE64Encoder()).encode(pMessage.getBytes(Charset.forName(GlobalConstant.CHARSET_UTF8)));
    }
}
