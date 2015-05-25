package com.fmc.edu.cache;

import java.util.LinkedHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheManager {

	public static final String CACHE_CONTENT_NEWS_LIKE = "news_like";

	private static CacheManager INSTANCE = new CacheManager();

	private LinkedHashMap<String, CacheContent> mCacheContentMap;


	/**
	 * protected constructor
	 */
	protected CacheManager() {
		mCacheContentMap = new LinkedHashMap<>();
	}

	/**
	 * @return singleton mode to make sure only one cache manager in application scope
	 */
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
