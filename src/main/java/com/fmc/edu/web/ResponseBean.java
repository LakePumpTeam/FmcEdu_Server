package com.fmc.edu.web;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/15/2015.
 */
public class ResponseBean {
    private Map<String, Object> responseData = new HashMap<String, Object>();

    public ResponseBean() {
        responseData.put(JSONOutputConstant.PARAM_STATUS, GlobalConstant.STATUS_SUCCESS);
        responseData.put(JSONOutputConstant.PARAM_MESSAGE, new ArrayList<String>());
        responseData.put(JSONOutputConstant.PARAM_DATA, new HashMap<String, Object>());

        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, true);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, StringUtils.EMPTY);
    }

    public void addBusinessMsg(final String pBussinessMsg) {
        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, false);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, pBussinessMsg);
    }

    public void addErrorMsg(final String pErrorMsg) {
        responseData.put(JSONOutputConstant.PARAM_STATUS, GlobalConstant.STATUS_ERROR);
        List<String> errors = (List<String>) responseData.get(JSONOutputConstant.PARAM_MESSAGE);
        errors.add(pErrorMsg);
    }

    public void addData(final String pKey, final Object pData) {
        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(pKey, pData);
    }

    @Override
    public String toString() {
        return encodeOutput(JSONObject.fromObject(responseData).toString());
    }

    protected String encodeOutput(final String pMessage) {
        ReplacementBase64EncryptService base64EncryptService = new ReplacementBase64EncryptService();
        return base64EncryptService.encrypt(pMessage);
    }
}
