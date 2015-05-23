package com.fmc.edu.cache.impl;

import com.fmc.edu.cache.ICacheExpiredHandler;
import com.fmc.edu.manager.NewsManager;

/**
 * Created by Yove on 5/24/2015.
 */
public class NewsLikeCacheExpiredHandler implements ICacheExpiredHandler {

	private NewsManager mNewsManager;

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(final NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}
}
