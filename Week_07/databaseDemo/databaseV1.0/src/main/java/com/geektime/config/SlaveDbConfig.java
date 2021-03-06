package com.geektime.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 从库数据源配置
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "com.geektime.slave" ,sqlSessionFactoryRef ="slaveSqlSessionFactory" )
public class SlaveDbConfig {

    private static final String MAPPER_LOCATION = "classpath:mapper/slave/*.xml";

    private static final String ENTITY_PACKAGE = "com.geektime.entity";

    @Value("${spring.datasource.order-slave.url}")
    private String url;

    @Value("${spring.datasource.order-slave.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.order-slave.username}")
    private String userName;

    @Value("${spring.datasource.order-slave.password}")
    private String password;

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }

    @Bean(name = "slaveDataSourceTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SlaveDbConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_PACKAGE);
        //mybatis数据库字段与实体类字段驼峰映射配置
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }


}
