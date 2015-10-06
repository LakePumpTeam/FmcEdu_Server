package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 6/7/2015.
 */
public abstract class AdminTransactionBaseController {

	protected static final String ERROR_MESSAGE = "errorMessage";

	protected static final String MESSAGE = "message";

	protected static final String REDIRECT_HOME = "redirect:/admin/home";

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Autowired
	private ResourceManager mResourceManager;

	protected TransactionStatus ensureTransaction() {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		return status;
	}

	protected Pagination buildPagination(HttpServletRequest pRequest) {
		String pageIndex = pRequest.getParameter("pageIndex");
		int index = StringUtils.isBlank(pageIndex) ? GlobalConstant.DEFAULT_PAGE_INDEX : Integer.valueOf(pageIndex);
		String pageSize = pRequest.getParameter("pageSize");
		int size = StringUtils.isBlank(pageSize) ? GlobalConstant.DEFAULT_PAGE_SIZE : Integer.valueOf(pageSize);
		return new Pagination(index, size);
	}

	protected Pagination buildDefaultPagination() {
		return new Pagination(1, Integer.MAX_VALUE);
	}

	protected String getCurrentRequestURIWithParameters(HttpServletRequest pRequest) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer(pRequest.getRequestURI());
		if (StringUtils.isNoneBlank(pRequest.getQueryString())) {
			sb.append("?").append(pRequest.getQueryString());
		}
		return URLEncoder.encode(sb.toString(), "UTF-8");
	}

	protected void addErrorMessage(Model pModel, String pMessage) {
		addMessage2Model(ERROR_MESSAGE, pModel, pMessage);
	}

	protected void addMessage(Model pModel, String pMessage) {
		addMessage2Model(MESSAGE, pModel, pMessage);
	}

	private void addMessage2Model(String pKey, Model pModel, String pMessage) {
		Map attributeMap = pModel.asMap();
		Object messageVector = attributeMap.get(pKey);
		List<String> messageList = null;
		if (messageVector instanceof List) {
			messageList = (List) messageVector;
			messageList.add(pMessage);
		} else {
			messageList = new ArrayList();
			messageList.add(pMessage);
			pModel.addAttribute(pKey, messageList);
		}
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public ResourceManager getResourceManager() {
		return mResourceManager;
	}

	public void setResourceManager(final ResourceManager pResourceManager) {
		mResourceManager = pResourceManager;
	}
}
