package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
public abstract class BaseController {

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Resource(name = "replacementBase64EncryptService")
	private ReplacementBase64EncryptService mBase64EncryptService;

	@Resource(name = "responseBean")
	private ResponseBean mResponseBean;

	protected int getStatusMapping(boolean pSuccess) {
		return pSuccess ? GlobalConstant.STATUS_SUCCESS : GlobalConstant.STATUS_ERROR;
	}

	protected String generateJsonOutput(boolean pSuccess, Object pJsonData, String pMessage) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(JSONOutputConstant.PARAM_STATUS, getStatusMapping(pSuccess));
		if (pJsonData == null) {
			pJsonData = StringUtils.EMPTY;
		}
		result.put(JSONOutputConstant.PARAM_DATA, pJsonData);
		if (pMessage == null) {
			pMessage = StringUtils.EMPTY;
		}
		result.put(JSONOutputConstant.PARAM_MESSAGE, pMessage);
		return encodeOutput(JSONObject.fromObject(result).toString());
	}

	protected String encodeOutput(final String pMessage) {
		return mBase64EncryptService.encrypt(pMessage);
	}

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

	protected void addException(final Exception pException) {
		getResponseBean().addErrorMsg(pException.getMessage());
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

	public ResponseBean getResponseBean() {
		return mResponseBean;
	}

	public void setResponseBean(ResponseBean pResponseBean) {
		mResponseBean = pResponseBean;
	}
}
