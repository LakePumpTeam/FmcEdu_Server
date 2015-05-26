package com.fmc.edu.cache.impl.newslike;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.ICacheExpiredHandler;
import com.fmc.edu.model.news.News;
import com.fmc.edu.service.impl.NewsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yove on 5/24/2015.
 */
@Service(value = "newsLikeCacheContent")
public class NewsLikeCacheContent extends CacheContent {

	private static final Logger LOG = Logger.getLogger(NewsLikeCacheContent.class);

	public static final String MINUS_ONE = "-";

	public static final String PLUS_ONE = "+";

	@Resource(name = "newsService")
	private NewsService mNewsService;

	@Resource(name = "newsLikeCacheExpiredHandler")
	private ICacheExpiredHandler mImplementCacheExpiredHandler;

	@Override
	public boolean updateCache(final String pCacheKey, final Map<String, Object> pParams) {
		ensureCacheCapacity();
		Cache cache = mCache.get(pCacheKey);
		String updateType = (String) pParams.get(UPDATE_TYPE);
		if (cache == null) {
			// the record had not been cached, sync all news like cache
			synchronized (this) {
				News news = getNewsService().queryNewsDetail(Integer.valueOf(pCacheKey));
				if (PLUS_ONE.equals(updateType)) {
					cache = new Cache(pCacheKey, news.getLike() + 1);
				} else if (MINUS_ONE.equals(updateType)) {
					cache = new Cache(pCacheKey, news.getLike() - 1);
				}
				// cache record
				cache.setChanged(true);
				mCache.putIfAbsent(pCacheKey, cache);
			}
		} else {
			// sync cache is enough
			synchronized (cache) {
				if (PLUS_ONE.equals(updateType)) {
					cache.setValue((int) cache.getValue() + 1);
				} else if (MINUS_ONE.equals(updateType)) {
					cache.setValue((int) cache.getValue() - 1);
				}
				cache.updateLastUpdateTime();
				cache.setChanged(true);
			}
		}
		return true;
	}

	@Override
	public void handleCacheExpiration() {
		LOG.debug(String.format("=========== Process cache expiration for scheduled at %s===========", new Date()));
		super.handleCacheExpiration();
		LOG.debug(String.format("=========== End cache expiration for scheduled at %s===============", new Date()));
	}

	public NewsService getNewsService() {
		return mNewsService;
	}

	public void setNewsService(final NewsService pNewsService) {
		mNewsService = pNewsService;
	}

	public ICacheExpiredHandler getImplementCacheExpiredHandler() {
		return mImplementCacheExpiredHandler;
	}

	public void setImplementCacheExpiredHandler(final ICacheExpiredHandler pImplementCacheExpiredHandler) {
		mImplementCacheExpiredHandler = pImplementCacheExpiredHandler;
	}

	@Override
	public ICacheExpiredHandler getCacheExpiredHandler() {
		if (getImplementCacheExpiredHandler() != null) {
			return getImplementCacheExpiredHandler();
		}
		return super.getCacheExpiredHandler();
	}

}
