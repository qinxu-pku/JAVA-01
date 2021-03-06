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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主库数据源配置
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "com.geektime.mapper.master",sqlSessionFactoryRef ="masterSqlSessionFactory" )
public class MasterDbConfig {

    private static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    private static final String ENTITY_PACKAGE = "com.geektime.entity";

    @Value("${spring.datasource.order-master.url}")
    private String url;

    @Value("${spring.datasource.order-master.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.order-master.username}")
    private String userName;

    @Value("${spring.datasource.order-master.password}")
    private String password;

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }

    @Bean(name = "masterDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDbConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_PACKAGE);
        //mybatis数据库字段与实体类字段驼峰映射配置
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }


}
