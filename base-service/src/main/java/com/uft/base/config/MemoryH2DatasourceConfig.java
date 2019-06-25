package com.uft.base.config;

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
@MapperScan(basePackages = "com.uft.base.sync.h2.dao", sqlSessionTemplateRef = "h2SqlSessionTemplate")
public class MemoryH2DatasourceConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @ConfigurationProperties(prefix = "h2db.datasource")
    public MemoryH2Config memoryH2Config(){
        MemoryH2Config memoryH2Config = new MemoryH2Config();
        return memoryH2Config;
    }

    @Bean(name = "h2Datasource")
    public DataSource dataSource(MemoryH2Config memoryH2Config) {
        return DataSourceBuilder.create()
                .type(memoryH2Config.getType())
                .driverClassName(memoryH2Config.getDriver_class_name())
                .password(memoryH2Config.getPassword())
                .username(memoryH2Config.getUsername())
                .url(memoryH2Config.getUrl())
                .build();
    }

    @Bean(name = "h2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("h2Datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        try {

            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/sync/h2/*.xml"));

            return factoryBean.getObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Bean(name="h2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("h2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Bean(name="h2TransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("h2Datasource")DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }

}
