package com.fmc.edu.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheContent {

	public static final String UPDATE_TYPE = "updateType";

	public static final String VALUE = "value";

	public boolean updateCache(String pCacheKey, Map<String, Object> pParams) {
		return true;
	}

	private ICacheExpiredHandler mCacheExpiredHandler;

	protected ConcurrentHashMap<String, Cache> mCache = new ConcurrentHashMap<>();

	protected long mLastRefreshTime = System.currentTimeMillis();

	protected long mCacheExpiredTime;

	protected ICacheExpiredHandler mExpiredHandler;

	public long getNextRefreshTime() {
		return mLastRefreshTime + mCacheExpiredTime;
	}

	public void cache(Cache pCache) {
		mCache.putIfAbsent(pCache.getKey(), pCache);
	}

	public ICacheExpiredHandler getCacheExpiredHandler() {
		return mCacheExpiredHandler;
	}

	public void setCacheExpiredHandler(final ICacheExpiredHandler pCacheExpiredHandler) {
		mCacheExpiredHandler = pCacheExpiredHandler;
	}
}
