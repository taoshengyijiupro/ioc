package com.shipparts.aop;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;


@Component
@Scope("prototype")
public class TransactionUtils {

    private DataSourceTransactionManager dataSourceTransactionManager;

    private TransactionStatus transactionStatus;

    //开启事务
    public TransactionStatus begin() {
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transaction;
    }

    //提交事务
    public void commit(TransactionStatus transaction) {
        dataSourceTransactionManager.commit(transaction);
    }

    //回滚事务
    public void rollback() {
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}
