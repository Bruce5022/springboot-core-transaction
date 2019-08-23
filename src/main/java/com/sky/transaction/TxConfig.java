package com.sky.transaction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务:
 * 环境搭建:
 * 1.导入相关依赖
 *  数据源,数据库驱动,Spring-jdbc模块
 * 2.配置数据源,JdbcTemplate(Spring提供的简化数据库操作的工具)操作数据
 * 3.给方法上标注@Transactional表示当前方法是一个事务方法
 * 4.开启基于注解的事务管理功能@EnableTransactionManagement
 * 5.配置事务管理器管理事务,如果不配置,也是执行异常
 *      No qualifying bean of type 'org.springframework.transaction.PlatformTransactionManager' available
 */

// spring的配置类中,都是bean,内部调用方法,其实spring做了处理,返回的还是那个bean
@EnableTransactionManagement
@Configuration
public class TxConfig {


    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setUser("root");
            ds.setPassword("Windows8");
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://49.234.60.118:3306/skydb");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        System.out.println("dataSource ds:" + ds.hashCode());
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {

        DataSource dataSource = dataSource();
        System.out.println("jdbcTemplate ds:" + dataSource.hashCode());
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}

//六月 16, 2019 8:20:55 上午 com.mchange.v2.log.MLog <clinit>
//信息: MLog clients using java 1.4+ standard logging.
//六月 16, 2019 8:20:56 上午 com.mchange.v2.c3p0.C3P0Registry banner
//信息: Initializing c3p0-0.9.1.2 [built 21-May-2007 15:04:56; debug? true; trace: 10]
//dataSource ds:1146290742
//jdbcTemplate ds:1146290742
//Process finished with exit code 0