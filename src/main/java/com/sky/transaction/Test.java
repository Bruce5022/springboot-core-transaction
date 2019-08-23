package com.sky.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Hello world!
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class, UserDao.class, UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.insertUser(null);

        DataSourceTransactionManager transactionManager = context.getBean(DataSourceTransactionManager.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        status = transactionManager.getTransaction(def); // 获得事务状态


        try {
            //逻辑代码，可以写上你的逻辑处理代码

            transactionManager.commit(status);

        } catch (Exception e) {

            transactionManager.rollback(status);

        }
    }
}
