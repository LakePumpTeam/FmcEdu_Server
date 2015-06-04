package com.fmc.edu.web.servlet;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Yu on 6/4/2015.
 */
public class Base64DecodeRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = Logger.getLogger(Base64DecodeRequestWrapper.class);
    private ReplacementBase64EncryptService mBase64EncryptService;

    public Base64DecodeRequestWrapper(HttpServletRequest request) {
        super(request);
        mBase64EncryptService = new ReplacementBase64EncryptService();
        LOG.debug(">>>>>>>>>>>>>>>Initialized Base64DecodeRequestWrapper>>>>>>>>>>>");
    }

    @Override
    public String getParameter(String pName) {
        String encodedValue = this.getRequest().getParameter(pName);
        LOG.debug(String.format(">>>>>>>>>>>>>>>Obtain Parameter:%s = %s", pName, encodedValue));
        String decodedValue = encodedValue;
        if (Base64.isBase64(encodedValue)) {
            try {
                decodedValue = decodeInput(encodedValue);
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        LOG.debug(String.format(">>>>>>>>>>>>>>>Decoded Parameter:%s = %s", pName, decodedValue));
        return decodedValue;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] encodedValues = super.getParameterValues(name);
        if (encodedValues == null || encodedValues.length == 0) {
            return encodedValues;
        }
        String[] decodedValues = new String[encodedValues.length];
        String decodedValue;
        for (int i = 0; i < encodedValues.length; i++) {
            decodedValue = encodedValues[i];
            if (Base64.isBase64(encodedValues[i])) {
                try {
                    decodedValue = decodeInput(encodedValues[i]);
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
            decodedValues[i] = decodedValue;
        }
        return decodedValues;
    }

    protected String decodeInput(final String pParameter) throws IOException {
        LOG.debug("Encoded input parameter:" + pParameter);
        if (!WebConfig.isEncodeBase64InputParam()) {
            return pParameter;
        }
        String decodeInput = null;
        decodeInput = mBase64EncryptService.decrypt(pParameter);
        LOG.debug("Decoded input parameter:" + decodeInput);
        return decodeInput;
    }
}
