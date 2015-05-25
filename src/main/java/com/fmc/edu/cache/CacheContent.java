package com.fmc.edu.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheContent {

	public static final String UPDATE_TYPE = "updateType";

	public static final String VALUE = "value";

	/**
	 * Handler to process expiration related operation
	 */
	private ICacheExpiredHandler mCacheExpiredHandler;

	protected ConcurrentHashMap<String, Cache> mCache = new ConcurrentHashMap<>();

	private long mLastUpdateTime = System.currentTimeMillis();

	protected ICacheExpiredHandler mExpiredHandler;

	public boolean updateCache(String pCacheKey, Map<String, Object> pParams) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return a copy of cache
	 */
	public Map<String, Cache> getCacheCopy() {
		return new HashMap<>(mCache);
	}

	/**
	 * invoke ICacheExpiredHandler#synchronizeExpiredCache to sync expiration cache to database
	 */
	public void handleCacheExpiration() {
		getCacheExpiredHandler().synchronizeExpiredCache(this);
	}

	/**
	 * clean all cache, and unpersist data will be lost
	 */
	public void clear() {
		mCache = new ConcurrentHashMap<>();
	}

	public void updateLastUpdateTime() {
		mLastUpdateTime = System.currentTimeMillis();
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

	public long getLastUpdateTime() {
		return mLastUpdateTime;
	}

	public void setLastUpdateTime(final long pLastUpdateTime) {
		mLastUpdateTime = pLastUpdateTime;
	}
}
