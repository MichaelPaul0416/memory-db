package com.paul.memory.concurrency.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
@Configuration
@MapperScan(basePackages = "com.paul.memory.concurrency.dao.oracle", sqlSessionTemplateRef = "oracleSqlSessionTemplate")
public class OracleDatasourceConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @ConfigurationProperties(prefix = "oracle.datasource")
    public OracleConfig sysOracleConfig(){
        return new OracleConfig();
    }

    @Bean(name = "oracleDatasource")
    @Primary
    public DataSource oracleDatasource(OracleConfig syncOracleConfig) {
        return DataSourceBuilder.create()
                .username(syncOracleConfig.getUsername())
                .url(syncOracleConfig.getUrl())
                .password(syncOracleConfig.getPassword())
                .driverClassName(syncOracleConfig.getDriver_class_name())
                .type(syncOracleConfig.getType())
                .build();
    }

    @Bean(name = "oracleSqlSessionFactory")
    @Primary
    public SqlSessionFactory syncSqlSessionFactory(@Qualifier("oracleDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            factoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/oracle/*.xml"));
            return factoryBean.getObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "oracleSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Bean(name="oracleTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("oracleDatasource")DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }
}
