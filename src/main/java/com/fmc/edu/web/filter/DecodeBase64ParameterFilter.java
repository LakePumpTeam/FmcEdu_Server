package com.fmc.edu.web.filter;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.URLUtils;
import com.fmc.edu.web.servlet.Base64DecodeRequestWrapper;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Yu on 6/4/2015.
 */
public class DecodeBase64ParameterFilter implements Filter {

	private boolean mEnabled;
	private static final Logger LOG = Logger.getLogger(DecodeBase64ParameterFilter.class);

	private boolean mInitialized = false;
	private String[] mDisablePrefixArray;
	private String[] mDisableSuffixArray;

	@Override
	public void init(FilterConfig pFilterConfig) throws ServletException {
		String enabled = pFilterConfig.getInitParameter("enabled");
		if (StringUtils.isBlank(enabled)) {
			setEnabled(true);
		} else {
			setEnabled(Boolean.valueOf(enabled));
		}
		LOG.debug("============== Initialized DecodeBase64ParameterFilter ==============");
	}

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse, FilterChain pFilterChain) throws IOException, ServletException {
		if (!isEnabled()) {
			pFilterChain.doFilter(pServletRequest, pServletResponse);
			return;
		}
		if (!mInitialized) {
			Initialize(pServletRequest);
		}
		if (pServletRequest instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) pServletRequest;
			String URI = httpServletRequest.getRequestURI();
			for (String disablePrefix : getDisablePrefixArray()) {
				if (!URLUtils.PrefixURLMatch(URI, disablePrefix)) {
					continue;
				}
				pFilterChain.doFilter(pServletRequest, pServletResponse);
				return;
			}
			for (String disableSuffix : getDisableSuffixArray()) {
				if (!URLUtils.SuffixURLMatch(URI, disableSuffix)) {
					continue;
				}
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

	private void Initialize(ServletRequest pRequest) {
		// override servlet context path
		String context = pRequest.getServletContext().getContextPath();
		ServletContext sc = pRequest.getServletContext();
		// check web context configuration
		Boolean enableWebConfigCtx = (Boolean) sc.getAttribute("enableWebConfigContext");
		if (enableWebConfigCtx != null && enableWebConfigCtx.booleanValue()) {
			// set context configured manually in web.properties
			context = WebConfig.getFMCWebContext();
			LOG.debug("Override context from web.properties: " + context);
		}
		LOG.debug("Get final context: " + context);
		// disable decode url parameters prefix/suffix URL set
		String[] prefixArray = (String[]) sc.getAttribute("disableDecodeURLPrefixArray");
		mDisablePrefixArray = new String[prefixArray.length];
		for (int i = 0; i < prefixArray.length; i++) {
			mDisablePrefixArray[i] = (context + prefixArray[i]).replace("//", "/");
		}
		String[] suffixArray = (String[]) sc.getAttribute("disableDecodeURLSuffixArray");
		mDisableSuffixArray = new String[suffixArray.length];
		for (int i = 0; i < suffixArray.length; i++) {
			mDisableSuffixArray[i] = (context + suffixArray[i]).replace(".", "");
		}
		LOG.debug("Set disable decode URL prefix: " + Arrays.toString(mDisablePrefixArray));
		LOG.debug("Set disable decode URL suffix: " + Arrays.toString(mDisableSuffixArray));
		mInitialized = true;
	}

	@Override
	public void destroy() {
		LOG.debug("============== Destroyed DecodeBase64ParameterFilter ==============");
	}

	public boolean isEnabled() {
		return mEnabled;
	}

	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
	}

	public String[] getDisablePrefixArray() {
		return mDisablePrefixArray;
	}

	public void setDisablePrefixArray(final String[] pDisablePrefixArray) {
		mDisablePrefixArray = pDisablePrefixArray;
	}

	public String[] getDisableSuffixArray() {
		return mDisableSuffixArray;
	}

	public void setDisableSuffixArray(final String[] pDisableSuffixArray) {
		mDisableSuffixArray = pDisableSuffixArray;
	}
}
