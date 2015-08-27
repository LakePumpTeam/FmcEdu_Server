package com.fmc.edu.service.impl;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.service.IMessageIdentifyService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

/**
 * Used to invoke the third part API to send sms message and save the response code to local database in order to the next verification.
 */
@Service(value = "messageIdentifyService")
public class MessageIdentifyService implements IMessageIdentifyService {
    private static Logger LOG = Logger.getLogger(MessageIdentifyService.class);

    private static final int LENGTH = 6;

    private CCPRestSmsSDK restAPI;

    private int mRetryCount;

    @Override
    public String sendIdentifyRequest(String pPhoneNumber) {
        init();

        String identifyCode = RandomStringUtils.randomNumeric(LENGTH);
        HashMap<String, Object> result = restAPI.sendTemplateSMS(pPhoneNumber, WebConfig.getCCPSMSTemplateId(), new String[]{identifyCode, String.valueOf(WebConfig.getIdentifyCodeSurvivalPeriod() / 60)});
        while (!parseSMSResponse(result) && (getRetryCount() > 1)) {
            setRetryCount(getRetryCount() - 1);
            result = restAPI.sendTemplateSMS(pPhoneNumber, WebConfig.getCCPSMSTemplateId(), new String[]{identifyCode, String.valueOf(WebConfig.getIdentifyCodeSurvivalPeriod() / 60)});
        }
        return identifyCode;
    }

    private void init() {
        setRetryCount(3);

        restAPI = new CCPRestSmsSDK();
        restAPI.init(WebConfig.getCCPSMSRestUrl(), WebConfig.getCCPSMSRestPort());

        restAPI.setAccount(WebConfig.getCCPAccountSID(), WebConfig.getCCPAuthToken());

        restAPI.setAppId(WebConfig.getCCPAppId());
    }

    private boolean parseSMSResponse(HashMap<String, Object> pResponse) {
        boolean sendSMSStatus = false;
        if ("000000".equals(pResponse.get("statusCode"))) {
            HashMap<String, Object> data = (HashMap<String, Object>) pResponse.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                LOG.debug("SMS RESPONSE: " + key + " = " + object);
            }
            sendSMSStatus = true;
        } else {
            try {
                LOG.debug("错误码=" + pResponse.get("statusCode") + " 错误信息= " + new String(pResponse.get("statusMsg").toString().getBytes(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOG.error("parseSMSResponse:" + e.getMessage());
            }
            sendSMSStatus = false;
        }
        return sendSMSStatus;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public void setRetryCount(int pRetryCount) {
        mRetryCount = pRetryCount;
    }
}
