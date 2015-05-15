package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Resource(name = "replacementBase64EncryptService")
	private ReplacementBase64EncryptService mBase64EncryptService;

	protected String decodeInput(final String pParameter) throws IOException {
		if (!WebConfig.isEncodeBase64InputParam()) {
			return pParameter;
		}
		return mBase64EncryptService.decrypt(pParameter);
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

	protected Pagination buildPagination(HttpServletRequest pRequest) throws IOException {
		String pageIndex = decodeInput(pRequest.getParameter("pageIndex"));
		String pageSize = decodeInput(pRequest.getParameter("pageSize"));
		return new Pagination(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
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
}
