package com.fmc.edu.web;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.executor.processor.InstantiationTracingBeanPostProcessor;
import com.fmc.edu.manager.ResourceManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu on 5/15/2015.
 */
public class ResponseBean {
    private Map<String, Object> responseData = new HashMap<String, Object>();

    private ResourceManager mResourceManager;

    private HttpServletRequest mHttpServletRequest;

    public ResponseBean() {
        this(null);
    }

    public ResponseBean(HttpServletRequest pRequest) {
        responseData.put(JSONOutputConstant.PARAM_STATUS, GlobalConstant.STATUS_SUCCESS);
        responseData.put(JSONOutputConstant.PARAM_MESSAGE, StringUtils.EMPTY);
        responseData.put(JSONOutputConstant.PARAM_DATA, new HashMap<String, Object>());

        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, GlobalConstant.STATUS_SUCCESS);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, StringUtils.EMPTY);

        mHttpServletRequest = pRequest;
        if (mHttpServletRequest == null) {
            WebApplicationContext webApplicationContext = (WebApplicationContext) mHttpServletRequest.getServletContext().getAttribute(InstantiationTracingBeanPostProcessor.FMC_ROOT_WEB_CURRENT_WEBAPPLICATIONCONTEXT);
            mResourceManager = (ResourceManager) webApplicationContext.getBean("resourceManager");
        }
        if (mResourceManager == null) {
            mResourceManager = new ResourceManager();
        }
    }

    public void addBusinessMsg(final String pMsgKey) {
        if (StringUtils.isBlank(pMsgKey)) {
            return;
        }

        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, GlobalConstant.STATUS_ERROR);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, mResourceManager.getMessage(mHttpServletRequest, pMsgKey));
    }

    public void addBusinessMsg(final String pMsgKey, String... pArgs) {
        if (StringUtils.isBlank(pMsgKey)) {
            return;
        }

        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, GlobalConstant.STATUS_ERROR);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, mResourceManager.getMessage(mHttpServletRequest, pMsgKey, pArgs));
    }

    public void addBusinessMsg(final String pMsgKey, String[] pArgs, String pMessage) {
        if (StringUtils.isBlank(pMsgKey)) {
            return;
        }

        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(JSONOutputConstant.BUSSINESS_IS_SUCCESS, GlobalConstant.STATUS_ERROR);
        dataMap.put(JSONOutputConstant.BUSSINESS_MESSAGE, mResourceManager.getMessage(mHttpServletRequest, pMsgKey, pArgs, pMessage));
    }

    public void addErrorMsg(final String pErrorMsg) {
        if (StringUtils.isBlank(pErrorMsg)) {
            return;
        }
        responseData.put(JSONOutputConstant.PARAM_STATUS, GlobalConstant.STATUS_ERROR);
        responseData.put(JSONOutputConstant.PARAM_MESSAGE, pErrorMsg);
    }

    public void addErrorMsg(final Exception pEx) {
        addErrorMsg(pEx.getMessage());
    }

    public void addData(final String pKey, final Object pData) {
        if (StringUtils.isBlank(pKey)) {
            return;
        }
        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.put(pKey, pData);
    }

    public void addData(final Map<String, ?> pData) {
        if (CollectionUtils.isEmpty(pData)) {
            return;
        }
        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        dataMap.putAll(pData);
    }

    public Boolean isSuccess() {
        Map<String, Object> dataMap = (Map<String, Object>) responseData.get(JSONOutputConstant.PARAM_DATA);
        return ((Integer) dataMap.get(JSONOutputConstant.BUSSINESS_IS_SUCCESS) == GlobalConstant.STATUS_SUCCESS) ? true : false;
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
