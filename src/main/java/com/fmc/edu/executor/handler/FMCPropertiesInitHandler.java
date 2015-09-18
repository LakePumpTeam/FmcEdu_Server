package com.fmc.edu.executor.handler;

import com.fmc.edu.executor.IInitializationHandler;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Yove on 8/24/2015.
 */
public class FMCPropertiesInitHandler implements IInitializationHandler {

	private static final Logger LOG = Logger.getLogger(FMCPropertiesInitHandler.class);

	private Map<Integer, String> mDeviceTypeMap;

	private String mDatePattern;

	private String[] mParentStudentRelationshipStrings;

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
		LOG.debug("Set date parentStudentRelationshipStrings: " + getParentStudentRelationshipStrings());
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
}
