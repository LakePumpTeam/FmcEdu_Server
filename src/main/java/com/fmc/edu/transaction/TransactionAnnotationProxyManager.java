package com.fmc.edu.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * Created by Yu on 6/4/2015.
 */
@Component("transactionAnnotationProxyManager")
public class TransactionAnnotationProxyManager {
    @Autowired
    private DataSourceTransactionManager transactionManager;

    public <T> T proxyFor(Object object) {
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
                new AnnotationTransactionInvocationHandler(object, transactionManager, null));
    }

    public <T> T proxyFor(Object object, Integer pTransactionDefinition) {
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
                new AnnotationTransactionInvocationHandler(object, transactionManager, pTransactionDefinition));
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager pTransactionManager) {
        transactionManager = pTransactionManager;
    }
}
