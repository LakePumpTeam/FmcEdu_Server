package com.fmc.edu.cache.impl.newslike;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.service.impl.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Yove on 5/24/2015.
 */
@Service(value = "newsLikeCacheContent")
public class NewsLikeCacheContent extends CacheContent {

	public static final String MINUS_ONE = "-";

	public static final String PLUS_ONE = "+";

	@Resource(name = "newsService")
	private NewsService mNewsService;


	@Override
	public boolean updateCache(final String pCacheKey, final Map<String, Object> pParams) {
		Cache cache = mCache.get(pCacheKey);
		Object value = pParams.get(VALUE);
		if (cache == null) {
			cache = new Cache(pCacheKey, value);
		} else {
			cache.setValue(value);
			cache.updateLastUpdateTime();
		}
		return mCache.putIfAbsent(pCacheKey, cache) != null;
	}

	public NewsService getNewsService() {
		return mNewsService;
	}

	public void setNewsService(final NewsService pNewsService) {
		mNewsService = pNewsService;
	}
}
