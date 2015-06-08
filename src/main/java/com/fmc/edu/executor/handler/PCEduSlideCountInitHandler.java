package com.fmc.edu.executor.handler;

import com.fmc.edu.executor.IInitializationHandler;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by Yove on 6/8/2015.
 */
public class PCEduSlideCountInitHandler implements IInitializationHandler {

	private static final Logger LOG = Logger.getLogger(PCEduSlideCountInitHandler.class);

	private int mSlideCount;

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		ServletContext servletContext = pWebApplicationContext.getServletContext();
		servletContext.setAttribute("slideCount", getSlideCount());
		LOG.debug(String.format("Set slide count to: %d", getSlideCount()));
	}

	public int getSlideCount() {
		return mSlideCount;
	}

	public void setSlideCount(final int pSlideCount) {
		mSlideCount = pSlideCount;
	}
}
