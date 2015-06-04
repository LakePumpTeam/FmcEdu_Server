package com.fmc.edu.transaction;

import com.fmc.edu.annotation.FMCTransactional;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Yu on 6/4/2015.
 */
public class AnnotationTransactionInvocationHandler implements InvocationHandler {
    private Object proxy;
    private DataSourceTransactionManager transactionManager;
    private Integer mTransactionDefinition;

    AnnotationTransactionInvocationHandler(Object object, DataSourceTransactionManager transactionManager, Integer pTransactionDefinition) {
        this.proxy = object;
        this.transactionManager = transactionManager;
        this.mTransactionDefinition = pTransactionDefinition;
    }

    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        Method originalMethod = proxy.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (!originalMethod.isAnnotationPresent(FMCTransactional.class)) {
            return method.invoke(proxy, objects);
        }

        TransactionStatus txStatus = ensureTransaction();
        Object result = null;
        try {
            result = method.invoke(proxy, objects);
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            throw e;
        } finally {
            transactionManager.commit(txStatus);
        }
        return result;
    }

    private TransactionStatus ensureTransaction() {
        if (mTransactionDefinition == null) {
            mTransactionDefinition = TransactionDefinition.PROPAGATION_REQUIRED;
        }
        DefaultTransactionDefinition mTransactionDefinition = new DefaultTransactionDefinition();
        mTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(mTransactionDefinition);
        return status;
    }
}
