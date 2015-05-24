package com.fmc.edu.cache;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
@Service(value = "cacheManager")
public class CacheManager {

	public static final String CACHE_CONTENT_NEWS_LIKE = "news_like";

	private static CacheManager INSTANCE = new CacheManager();

	private LinkedHashMap<String, CacheContent> mCacheContentMap;

	protected CacheManager() {
		mCacheContentMap = new LinkedHashMap<>();
	}

	public static CacheManager getInstance() {
		return INSTANCE;
	}

	public boolean addCacheContent(String pCacheContentKey, CacheContent pCacheContent) {
		return mCacheContentMap.putIfAbsent(pCacheContentKey, pCacheContent) != null;
	}

	public boolean removeCacheContent(String pCacheContentKey) {
		return mCacheContentMap.remove(pCacheContentKey) != null;
	}

	public CacheContent getCacheContent(String pCacheContentKey) {
		return mCacheContentMap.get(CACHE_CONTENT_NEWS_LIKE);
	}

}
