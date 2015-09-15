package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.executor.IInitializationHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * Created by Yove on 9/15/2015.
 */
@Controller
@RequestMapping("/admin/sys/")
public class AdminSystemController implements ServletContextAware {

	@Resource(name = "locationInitHandler")
	private IInitializationHandler mLocationInitHandler;

	private ServletContext mServletContext;


	@RequestMapping("/main-sys" + GlobalConstant.URL_SUFFIX)
	public String forwardMainSystem() {
		return "admin/sys/main";
	}

	@RequestMapping("/invalid-location-cache" + GlobalConstant.URL_SUFFIX)
	public String invalidLocationCache() {
		getLocationInitHandler().initialize(WebApplicationContextUtils.getWebApplicationContext(mServletContext));
		return "redirect:main-sys";
	}

	@Override
	public void setServletContext(final ServletContext pServletContext) {
		mServletContext = pServletContext;
	}

	public IInitializationHandler getLocationInitHandler() {
		return mLocationInitHandler;
	}

	public void setLocationInitHandler(final IInitializationHandler pLocationInitHandler) {
		mLocationInitHandler = pLocationInitHandler;
	}

}
