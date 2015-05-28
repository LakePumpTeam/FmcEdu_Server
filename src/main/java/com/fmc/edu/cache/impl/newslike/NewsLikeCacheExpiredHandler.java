package com.fmc.edu.cache.impl.newslike;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.ICacheExpiredHandler;
import com.fmc.edu.service.impl.NewsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yove on 5/24/2015.
 */
@Service(value = "newsLikeCacheExpiredHandler")
public class NewsLikeCacheExpiredHandler implements ICacheExpiredHandler {

	private static final Logger LOG = Logger.getLogger(NewsLikeCacheExpiredHandler.class);

	@Resource(name = "newsService")
	private NewsService mNewsService;

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Override
	public synchronized void synchronizeExpiredCache(final CacheContent pCacheContent, final boolean pForceInitialize) {
		// get cache copy
		List<Cache> cacheCopy = new ArrayList<>(pCacheContent.getCacheCopy().values());
		// remove caches which were not changed
		if (CollectionUtils.isEmpty(cacheCopy)) {
			LOG.debug("====== Oops, NO CHANGES should be persist to database.");
			return;
		}
		// persist changes of like number
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		try {
			// update changes within a batch update
			boolean result = getNewsService().updateNewsCacheBatch(cacheCopy);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
			// clear cache if the transaction commit successfully
			if (!status.isRollbackOnly()) {
				pCacheContent.clear();
			}
		}
	}

	public NewsService getNewsService() {
		return mNewsService;
	}

	public void setNewsService(final NewsService pNewsService) {
		mNewsService = pNewsService;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

}
