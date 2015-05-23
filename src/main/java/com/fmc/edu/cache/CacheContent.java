package com.fmc.edu.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yove on 5/23/2015.
 */
public class CacheContent {

	private ConcurrentHashMap<String, Cache> mCache = new ConcurrentHashMap<>();

	private long mLastRefreshTime = System.currentTimeMillis();

	private long mCacheExpiredTime;

	private ICacheExpiredHandler mExpiredHandler;

	public long getNextRefreshTime() {
		return mLastRefreshTime + mCacheExpiredTime;
	}
}
