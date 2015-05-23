package com.fmc.edu.executor.handler;

import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.executor.IInitializationHandler;
import com.fmc.edu.manager.NewsManager;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Yove on 5/24/2015.
 */
public class NewsCacheInitHandler implements IInitializationHandler {

	private NewsManager mNewsManager;

	private CacheManager mCacheManager = CacheManager.getInstance();

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		CacheContent newsCacheContent = new CacheContent();
		//TODO get like count for default news
		mCacheManager.addCacheContent(CacheManager.NEW_LIKE_CACHE, newsCacheContent);
	}

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(final NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}

}
