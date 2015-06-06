package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.transaction.TransactionAnnotationProxyManager;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {

    private static final Logger LOG = Logger.getLogger(BaseController.class);

    @Resource(name = "resourceManager")
    private ResourceManager mResourceManager;

    @Autowired
    private DataSourceTransactionManager mTransactionManager;

    @Resource(name = "transactionAnnotationProxyManager")
    TransactionAnnotationProxyManager mTransactionAnnotationProxyManager;

    private CacheManager mCacheManager = CacheManager.getInstance();

    private WebApplicationContext mWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();

    protected String output(final ResponseBean pResponseBean) {
        LOG.debug("Response message:" + pResponseBean.toString());
        return pResponseBean.toString();
    }

    protected TransactionStatus ensureTransaction() {
        return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(pPropagationBehavior);
        TransactionStatus status = getTransactionManager().getTransaction(def);
        return status;
    }

    protected void validatePaginationParameters(final HttpServletRequest pRequest, ResponseBean responseBean) {
        String pageIndex = pRequest.getParameter("pageIndex");
        String pageSize = pRequest.getParameter("pageSize");
        if (StringUtils.isBlank(pageIndex)) {
            responseBean.addBusinessMsg("pageIndex is null.");
        }
        if (StringUtils.isBlank(pageSize)) {
            responseBean.addBusinessMsg("pageSize is null.");
        }
    }

    protected Pagination buildPagination(HttpServletRequest pRequest) throws IOException {
        String pageIndex = pRequest.getParameter("pageIndex");
        String pageSize = pRequest.getParameter("pageSize");
        return new Pagination(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
    }

    protected int getProfileIdFromSession(HttpServletRequest pRequest) {
        Object profileId = pRequest.getSession().getAttribute(SessionConstant.SESSION_KEY_PROFILE_ID);
        return profileId != null ? Integer.valueOf(profileId.toString()) : 0;
    }

    protected int[] decodeInputIds(final String[] pInputIds) throws IOException {
        int[] ids = new int[pInputIds.length];
        for (int i = 0; i < pInputIds.length; i++) {
            LOG.debug("decodeInputIds:" + pInputIds[i]);
            ids[i] = Integer.valueOf(pInputIds[i]);
        }
        return ids;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return mTransactionManager;
    }

    public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
        mTransactionManager = pTransactionManager;
    }

    public WebApplicationContext getWebApplicationContext() {
        return mWebApplicationContext;
    }

    public void setWebApplicationContext(final WebApplicationContext pWebApplicationContext) {
        mWebApplicationContext = pWebApplicationContext;
    }

    public CacheManager getCacheManager() {
        return mCacheManager;
    }

    public void setCacheManager(final CacheManager pCacheManager) {
        mCacheManager = pCacheManager;
    }

    public TransactionAnnotationProxyManager getTransactionAnnotationProxyManager() {
        return mTransactionAnnotationProxyManager;
    }

    public void setTransactionAnnotationProxyManager(TransactionAnnotationProxyManager pTransactionAnnotationProxyManager) {
        mTransactionAnnotationProxyManager = pTransactionAnnotationProxyManager;
    }

    public ResourceManager getResourceManager() {
        return mResourceManager;
    }

    public void setResourceManager(ResourceManager pResourceManager) {
        mResourceManager = pResourceManager;
    }
}
