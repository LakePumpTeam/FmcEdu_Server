package com.fmc.edu.cache;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheContent {

	private static final Logger LOG = Logger.getLogger(CacheContent.class);

	public static final String UPDATE_TYPE = "updateType";

	public static final String VALUE = "value";

	/**
	 * Handler to process expiration related operation
	 */
	private ICacheExpiredHandler mCacheExpiredHandler;

	protected ConcurrentHashMap<String, Cache> mCache = new ConcurrentHashMap<>();

	private long mLastUpdateTime = System.currentTimeMillis();

	protected ICacheExpiredHandler mExpiredHandler;

	private int mMaxCapacity = 100;

	public boolean updateCache(String pCacheKey, Map<String, Object> pParams) {
		throw new UnsupportedOperationException();
	}

	protected void ensureCacheCapacity() {
		synchronized (this) {
			if (mCache.size() >= getMaxCapacity()) {
				LOG.debug(String.format("=========== Process cache expiration for max capacity at %s===========", new Date()));
				getCacheExpiredHandler().synchronizeExpiredCache(this, true);
				LOG.debug(String.format("=========== End cache expiration for max capacity at %s===============", new Date()));
			}
		}
	}

	/**
	 * @return a copy of cache
	 */
	public Map<String, Cache> getCacheCopy() {
		return new HashMap<>(mCache);
	}

	public Object getCachedValue(String pCacheKey) {
		return getCachedValue(pCacheKey, null);
	}

	public Object getCachedValue(String pCacheKey, Object pDefaultValue) {
		Cache cache = mCache.get(pCacheKey);
		return cache != null ? cache.getValue() : pDefaultValue;
	}

	/**
	 * invoke ICacheExpiredHandler#synchronizeExpiredCache to sync expiration cache to database
	 */
	public void handleCacheExpiration() {
		getCacheExpiredHandler().synchronizeExpiredCache(this, false);
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

	public int getMaxCapacity() {
		return mMaxCapacity;
	}

	public void setMaxCapacity(final int pMaxCapacity) {
		mMaxCapacity = pMaxCapacity;
	}
}
