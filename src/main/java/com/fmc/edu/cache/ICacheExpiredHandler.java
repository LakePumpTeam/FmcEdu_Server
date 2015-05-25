package com.fmc.edu.cache;

/**
 * Created by Yove on 5/23/2015.
 */
public interface ICacheExpiredHandler {

	/**
	 * Sync expiration cache to database
	 *
	 * @param pCacheContent
	 */
	void synchronizeExpiredCache(CacheContent pCacheContent);
}
