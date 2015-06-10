package com.fmc.edu.web.filter;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.servlet.Base64DecodeRequestWrapper;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Yu on 6/4/2015.
 */
public class DecodeBase64ParameterFilter implements Filter {

    private boolean mEnabled;
    private String[] mDisablePrefixArray;
    private static final Logger LOG = Logger.getLogger(DecodeBase64ParameterFilter.class);

    @Override
    public void init(FilterConfig pFilterConfig) throws ServletException {
        String enabled = pFilterConfig.getInitParameter("enabled");
        if (StringUtils.isBlank(enabled)) {
            setEnabled(true);
        } else {
            setEnabled(Boolean.valueOf(enabled));
        }
        mDisablePrefixArray = pFilterConfig.getInitParameter("disablePrefixArray").split(",");
        for (int i = 0; i < mDisablePrefixArray.length; i++) {
            mDisablePrefixArray[i] = (WebConfig.getFMCWebContext() + mDisablePrefixArray[i]).replace("//", "/");
        }
        LOG.debug(">>>>>>>>>>>>>>Initialized DecodeBase64ParameterFilter>>>>>>");
    }

    @Override
    public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse, FilterChain pFilterChain) throws IOException, ServletException {
        if (!isEnabled()) {
            pFilterChain.doFilter(pServletRequest, pServletResponse);
            return;
        }
        if (pServletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) pServletRequest;
            for (String disablePrefix : mDisablePrefixArray) {
                LOG.debug(">>>>>>>>>>>>>>Current Request URI:" + httpServletRequest.getRequestURI() + ">>>disablePrefix:" + disablePrefix);
                if (!httpServletRequest.getRequestURI().startsWith(disablePrefix)) {
                    continue;
                }
                LOG.debug(">>>>>>>>>>>>>>SKIP: Request URI:" + httpServletRequest.getRequestURI());
                pFilterChain.doFilter(pServletRequest, pServletResponse);
                return;
            }
        }
        if (pServletRequest != null && ServletFileUpload.isMultipartContent((HttpServletRequest) pServletRequest)) {
            pFilterChain.doFilter(pServletRequest, pServletResponse);
            return;
        }
        HttpServletRequestWrapper requestWrapper = new Base64DecodeRequestWrapper((HttpServletRequest) pServletRequest);
        pFilterChain.doFilter(requestWrapper, pServletResponse);
    }

    @Override
    public void destroy() {
        LOG.debug(">>>>>>>>>>>>>>Destroyed DecodeBase64ParameterFilter>>>>>>");
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean pEnabled) {
        mEnabled = pEnabled;
    }
}
