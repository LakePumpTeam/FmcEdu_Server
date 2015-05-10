package com.fmc.edu.executor.processor;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Date;

/**
 * Created by Yove on 5/1/2015.
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOG = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);

	private static final String ROOT_WEB_APPLICATIONCONTEXT = "Root WebApplicationContext";
	private static final String GENERIC_APPICATION_CONTEXT = "GenericApplicationContext";

	/**
	 * @see ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent pEvent) {

		ApplicationContext applicationContext = pEvent
				.getApplicationContext();
		if (applicationContext.getDisplayName().contains(GENERIC_APPICATION_CONTEXT)) {
			LOG.debug("**********************Spring test framework initialized application context*****************");
			return;
		}
		if (ROOT_WEB_APPLICATIONCONTEXT.equals(applicationContext.getDisplayName())) {
			//Spring Context initialized
			LOG.debug("**********************Root ApplicationContext Loaded Completed*****************");
		} else {
			//Web context initialized.
			LOG.debug("**********************WebApplicationContext Loaded Completed*****************");

			// get the parent context
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext.getServletContext();
			// TODO load init attributes to application context
			servletContext.setAttribute("launchDate", new Date());
		}

	}
}
