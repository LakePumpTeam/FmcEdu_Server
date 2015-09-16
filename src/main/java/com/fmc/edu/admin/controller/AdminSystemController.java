package com.fmc.edu.admin.controller;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.cache.impl.newslike.NewsLikeCacheContent;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.executor.IInitializationHandler;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Yove on 9/15/2015.
 */
@Controller
@RequestMapping("/admin/sys")
public class AdminSystemController implements ServletContextAware {

	@Resource(name = "locationInitHandler")
	private IInitializationHandler mLocationInitHandler;

	private CacheManager mCacheManager = CacheManager.getInstance();

	private ServletContext mServletContext;


	@RequestMapping("/main-sys" + GlobalConstant.URL_SUFFIX)
	public String forwardMainSystem(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel) {
		int cachedNewLike = 0;
		NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
		for (Map.Entry<String, Cache> entry : cacheContent.getCacheCopy().entrySet()) {
			cachedNewLike += RepositoryUtils.safeParseId(String.valueOf(entry.getValue().getValue()));
		}
		pModel.addAttribute("cachedNewLike", cachedNewLike);
		return "admin/sys/sys-main";
	}

	@RequestMapping("/invalid-location-cache" + GlobalConstant.URL_SUFFIX)
	public String invalidLocationCache() {
		getLocationInitHandler().initialize(WebApplicationContextUtils.getWebApplicationContext(mServletContext));
		return "redirect:main-sys";
	}

	@RequestMapping("/persist-news-like-cache" + GlobalConstant.URL_SUFFIX)
	public String persistNewsLike() {
		NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
		cacheContent.handleCacheExpiration();
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

	public CacheManager getCacheManager() {
		return mCacheManager;
	}

	public void setCacheManager(final CacheManager pCacheManager) {
		mCacheManager = pCacheManager;
	}
}
