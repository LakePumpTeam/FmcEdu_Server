package com.fmc.edu.cache;

import java.util.LinkedHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheManager {

	public static final String NEW_LIKE_CACHE = "news_like";

	private static CacheManager INSTANCE = new CacheManager();

	private LinkedHashMap<String, CacheContent> mCacheContentMap;

	protected CacheManager() {
		INSTANCE = new CacheManager();
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


}
