package com.fmc.edu.executor.handler;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.executor.IInitializationHandler;
import com.fmc.edu.service.impl.NewsService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * Created by Yove on 5/24/2015.
 */
public class NewsCacheInitHandler implements IInitializationHandler {

	@Resource(name = "newsService")
	private NewsService mNewsService;

	@Resource(name = "newsLikeCacheContent")
	private CacheContent mCacheContent;

	private int mInitializeNewsCacheCount;

	private int mNewsType;

	private CacheManager mCacheManager = CacheManager.getInstance();

	@Override
	public synchronized void initialize(final WebApplicationContext pWebApplicationContext) {
		//		mCacheContent.clear();
		//		List<News> initNewsList = getNewsService().queryNewsListByNewType(buildPagination(), getNewsType());
		//		for (News news : initNewsList) {
		//			Cache cache = initCache(news.getId(), news.getLike());
		//			mCacheContent.cache(cache);
		//		}
		mCacheManager.addCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE, mCacheContent);
	}

	public Cache initCache(int pNewsId, int pLikeCount) {
		return new Cache(String.valueOf(pNewsId), pLikeCount);
	}

	protected Pagination buildPagination() {
		return new Pagination(1, getInitializeNewsCacheCount());
	}

	public NewsService getNewsService() {
		return mNewsService;
	}

	public void setNewsService(final NewsService pNewsService) {
		mNewsService = pNewsService;
	}

	public int getInitializeNewsCacheCount() {
		return mInitializeNewsCacheCount;
	}

	public void setInitializeNewsCacheCount(final int pInitializeNewsCacheCount) {
		mInitializeNewsCacheCount = pInitializeNewsCacheCount;
	}

	public int getNewsType() {
		return mNewsType;
	}

	public void setNewsType(final int pNewsType) {
		mNewsType = pNewsType;
	}

	public CacheManager getCacheManager() {
		return mCacheManager;
	}

	public void setCacheManager(final CacheManager pCacheManager) {
		mCacheManager = pCacheManager;
	}

	public CacheContent getCacheContent() {
		return mCacheContent;
	}

	public void setCacheContent(final CacheContent pCacheContent) {
		mCacheContent = pCacheContent;
	}
}
