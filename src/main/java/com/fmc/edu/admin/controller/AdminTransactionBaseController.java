package com.fmc.edu.admin.controller;

import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by Yove on 6/7/2015.
 */
public abstract class AdminTransactionBaseController {

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	protected TransactionStatus ensureTransaction() {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		return status;
	}

	protected Pagination buildDefaultPagination() {
		return new Pagination(1, Integer.MAX_VALUE);
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}
}
