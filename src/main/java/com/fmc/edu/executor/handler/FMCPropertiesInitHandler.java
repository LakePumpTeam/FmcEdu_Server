package com.fmc.edu.executor.handler;

import com.fmc.edu.executor.IInitializationHandler;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Yove on 8/24/2015.
 */
public class FMCPropertiesInitHandler implements IInitializationHandler {

	private static final Logger LOG = Logger.getLogger(FMCPropertiesInitHandler.class);

	private Map<Integer, String> mDeviceTypeMap;

	private String mDatePattern;

	private String[] mParentStudentRelationshipStrings;

	private boolean mEnableWebConfigContext;

	private String[] mDisableDecodeURLPrefixArray;

	private String[] mDisableDecodeURLSuffixArray;


	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		ServletContext servletContext = pWebApplicationContext.getServletContext();
		// init device types map
		servletContext.setAttribute("deviceTypeMap", getDeviceTypeMap());
		LOG.debug("Set device type map: " + getDeviceTypeMap());
		// init date pattern
		servletContext.setAttribute("datePattern", getDatePattern());
		LOG.debug("Set date pattern: " + getDatePattern());
		// init parent-student relationships
		servletContext.setAttribute("parentStudentRelationshipStrings", getParentStudentRelationshipStrings());
		LOG.debug("Set date parentStudentRelationshipStrings: " + Arrays.toString(getParentStudentRelationshipStrings()));

		// init enable web config context
		servletContext.setAttribute("enableWebConfigContext", isEnableWebConfigContext());
		LOG.debug("Set enable web config context: " + isEnableWebConfigContext());
		// init disable decode URL prefix array
		servletContext.setAttribute("disableDecodeURLPrefixArray", getDisableDecodeURLPrefixArray());
		LOG.debug("Set disable decode URL prefix array: " + Arrays.toString(getDisableDecodeURLPrefixArray()));
		// init disable decode URL suffix array
		servletContext.setAttribute("disableDecodeURLSuffixArray", getDisableDecodeURLSuffixArray());
		LOG.debug("Set disable decode URL suffix array: " + Arrays.toString(getDisableDecodeURLSuffixArray()));
	}

	public Map<Integer, String> getDeviceTypeMap() {
		return mDeviceTypeMap;
	}

	public void setDeviceTypeMap(final Map<Integer, String> pDeviceTypeMap) {
		mDeviceTypeMap = pDeviceTypeMap;
	}

	public String getDatePattern() {
		return mDatePattern;
	}

	public void setDatePattern(final String pDatePattern) {
		mDatePattern = pDatePattern;
	}

	public String[] getParentStudentRelationshipStrings() {
		return mParentStudentRelationshipStrings;
	}

	public void setParentStudentRelationshipStrings(final String[] pParentStudentRelationshipStrings) {
		mParentStudentRelationshipStrings = pParentStudentRelationshipStrings;
	}

	public boolean isEnableWebConfigContext() {
		return mEnableWebConfigContext;
	}

	public void setEnableWebConfigContext(final boolean pEnableWebConfigContext) {
		mEnableWebConfigContext = pEnableWebConfigContext;
	}

	public String[] getDisableDecodeURLPrefixArray() {
		return mDisableDecodeURLPrefixArray;
	}

	public void setDisableDecodeURLPrefixArray(final String[] pDisableDecodeURLPrefixArray) {
		mDisableDecodeURLPrefixArray = pDisableDecodeURLPrefixArray;
	}

	public String[] getDisableDecodeURLSuffixArray() {
		return mDisableDecodeURLSuffixArray;
	}

	public void setDisableDecodeURLSuffixArray(final String[] pDisableDecodeURLSuffixArray) {
		mDisableDecodeURLSuffixArray = pDisableDecodeURLSuffixArray;
	}
}
