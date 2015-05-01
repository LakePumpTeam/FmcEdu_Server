package com.fmc.edu.executor.processor;

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

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent pEvent) {
		// get the parent context
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		// TODO load init attributes to application context
		servletContext.setAttribute("launchDate", new Date());
	}
}
