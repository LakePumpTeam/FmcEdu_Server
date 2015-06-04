package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
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

    @Autowired
    private DataSourceTransactionManager mTransactionManager;

    @Resource(name = "replacementBase64EncryptService")
    private ReplacementBase64EncryptService mBase64EncryptService;

    @Resource(name = "transactionAnnotationProxyManager")
    TransactionAnnotationProxyManager mTransactionAnnotationProxyManager;

    private CacheManager mCacheManager = CacheManager.getInstance();

    private WebApplicationContext mWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();

    protected String output(final ResponseBean pResponseBean) {
        LOG.debug("Response message:" + pResponseBean.toString());
        return pResponseBean.toString();
    }

    protected String decodeInput(final String pParameter) throws IOException {
        LOG.debug("Encoded input parameter:" + pParameter);
        if (!WebConfig.isEncodeBase64InputParam()) {
            return pParameter;
        }
        String decodeInput = null;
        decodeInput = mBase64EncryptService.decrypt(pParameter);
        LOG.debug("Decoded input parameter:" + decodeInput);
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

    protected String[] decodeArrayInput(final String[] pArrayInput) throws IOException {
        if (pArrayInput == null || pArrayInput.length == 0) {
            return new String[0];
        }

        String[] arrayInput = new String[pArrayInput.length];
        for (int i = 0; i < pArrayInput.length; i++) {
            LOG.debug("decodeArrayInput:" + pArrayInput[i]);
            arrayInput[i] = decodeInput(pArrayInput[i]);
        }
        return arrayInput;
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

    public TransactionAnnotationProxyManager getTransactionAnnotationProxyManager() {
        return mTransactionAnnotationProxyManager;
    }

    public void setTransactionAnnotationProxyManager(TransactionAnnotationProxyManager pTransactionAnnotationProxyManager) {
        mTransactionAnnotationProxyManager = pTransactionAnnotationProxyManager;
    }
}
