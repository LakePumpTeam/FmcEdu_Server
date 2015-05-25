package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.exception.EncryptException;
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

    @Autowired
    private DataSourceTransactionManager mTransactionManager;

    @Resource(name = "replacementBase64EncryptService")
    private ReplacementBase64EncryptService mBase64EncryptService;

    @Resource(name = "cacheManager")
    private CacheManager mCacheManager;

    private WebApplicationContext mWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();

    protected String output(final ResponseBean pResponseBean) {
        LOG.debug("Response message:" + pResponseBean.toString());
        return pResponseBean.toString();
    }

    protected String decodeInput(final String pParameter) throws IOException {
        LOG.debug("Encode input parameter:" + pParameter);
        if (!WebConfig.isEncodeBase64InputParam()) {
            return pParameter;
        }
        String decodeInput = null;
        try {
            decodeInput = mBase64EncryptService.decrypt(pParameter);
        } catch (EncryptException e) {
            e.printStackTrace();
            LOG.error(e);
        }
        LOG.debug("Decode input parameter:" + decodeInput);
        return decodeInput;
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
        String pageIndex = decodeInput(pRequest.getParameter("pageIndex"));
        String pageSize = decodeInput(pRequest.getParameter("pageSize"));
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
            ids[i] = Integer.valueOf(decodeInput(pInputIds[i]));
        }
        return ids;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return mTransactionManager;
    }

    public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
        mTransactionManager = pTransactionManager;
    }

    public ReplacementBase64EncryptService getBase64EncryptService() {
        return mBase64EncryptService;
    }

    public void setBase64EncryptService(final ReplacementBase64EncryptService pBase64EncryptService) {
        mBase64EncryptService = pBase64EncryptService;
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
}
