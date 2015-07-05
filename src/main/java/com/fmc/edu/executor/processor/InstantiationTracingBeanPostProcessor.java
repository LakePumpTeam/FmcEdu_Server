package com.fmc.edu.executor.processor;

import com.fmc.edu.executor.IInitializationHandler;
import org.apache.commons.lang3.ArrayUtils;
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

    private static final String ROOT_WEB_APPLICATION_CONTEXT = "Root WebApplicationContext";
    private static final String GENERIC_APPLICATION_CONTEXT = "GenericApplicationContext";
    public static final String FMC_ROOT_WEB_CURRENT_WEBAPPLICATIONCONTEXT = "FMC.ROOT.WEB.CurrentWebApplicationContext";
    private IInitializationHandler[] mInitializationHandlers;

    /**
     * @see ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent pEvent) {

        ApplicationContext applicationContext = pEvent.getApplicationContext();
        if (applicationContext.getDisplayName().contains(GENERIC_APPLICATION_CONTEXT)) {
            LOG.debug("===================== SpringTestFramework Initialized ApplicationContext ===============");
            return;
        }
        if (ROOT_WEB_APPLICATION_CONTEXT.equals(applicationContext.getDisplayName())) {
            //Spring Context initialized
            LOG.debug("===================== RootApplicationContext Loaded Completed ===============");
        } else {
            //Web context initialized.
            LOG.debug("===================== WebApplicationContext Loaded Completed ===============");

            // get the parent context
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();
            // TODO load init attributes to application context
            servletContext.setAttribute("launchDate", new Date());
            servletContext.setAttribute(FMC_ROOT_WEB_CURRENT_WEBAPPLICATIONCONTEXT, webApplicationContext);
            invokeInitializationHandlers(webApplicationContext);
        }

    }

    protected void invokeInitializationHandlers(WebApplicationContext pWebApplicationContext) {
        if (ArrayUtils.isEmpty(mInitializationHandlers)) {
            return;
        }
        for (IInitializationHandler handler : mInitializationHandlers) {
            handler.initialize(pWebApplicationContext);
        }
    }

    public void setInitializationHandlers(final IInitializationHandler[] pInitializationHandlers) {
        mInitializationHandlers = pInitializationHandlers;
    }
}
