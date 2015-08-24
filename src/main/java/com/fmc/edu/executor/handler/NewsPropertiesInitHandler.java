package com.fmc.edu.executor.handler;

import com.fmc.edu.executor.IInitializationHandler;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Yove on 6/8/2015.
 */
public class NewsPropertiesInitHandler implements IInitializationHandler {

	private static final Logger LOG = Logger.getLogger(NewsPropertiesInitHandler.class);

	private int mSlideCount;

	private String mDatetimePattern;

	private Map<String, String> mNewsTypeMap;

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		ServletContext servletContext = pWebApplicationContext.getServletContext();
		// init slide count
		servletContext.setAttribute("slideCount", getSlideCount());
		LOG.debug(String.format("Set slide count to: %d", getSlideCount()));
		// init news type map
		servletContext.setAttribute("newsType", getNewsTypeMap());
		LOG.debug("Set new types: " + getNewsTypeMap().toString());
		// init date time pattern
		servletContext.setAttribute("datetimePattern", getDatetimePattern());
		LOG.debug("Set datetime pattern: " + getDatetimePattern());
	}

	public int getSlideCount() {
		return mSlideCount;
	}

	public void setSlideCount(final int pSlideCount) {
		mSlideCount = pSlideCount;
	}

	public Map<String, String> getNewsTypeMap() {
		return mNewsTypeMap;
	}

	public void setNewsTypeMap(final Map<String, String> pNewsTypeMap) {
		mNewsTypeMap = pNewsTypeMap;
	}

	public String getDatetimePattern() {
		return mDatetimePattern;
	}

	public void setDatetimePattern(final String pDatetimePattern) {
		mDatetimePattern = pDatetimePattern;
	}
}
