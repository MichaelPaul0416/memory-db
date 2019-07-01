//package com.paul.base.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//
///**
// * @Author: wangqiang20995
// * @Date:2019/6/25
// * @Description:
// * @Resource:
// */
//@Configuration
//@MapperScan(basePackages = "com.paul.base.sync.oracle.asset.dao", sqlSessionTemplateRef = "assetSqlSessionTemplate")
//public class HsAssetOracleDatasourceConfig {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Bean
//    @ConfigurationProperties(prefix = "asset.datasource")
//    public HsAssetOracleConfig hsAssetOracleConfig(){
//        return new HsAssetOracleConfig();
//    }
//
//    @Bean(name = "assetOracleDatasource")
////    @Primary
//    public DataSource oracleDatasource(HsAssetOracleConfig hsAssetOracleConfig) {
//        return DataSourceBuilder.create()
//                .username(hsAssetOracleConfig.getUsername())
//                .url(hsAssetOracleConfig.getUrl())
//                .password(hsAssetOracleConfig.getPassword())
//                .driverClassName(hsAssetOracleConfig.getDriver_class_name())
//                .type(hsAssetOracleConfig.getType())
//                .build();
//    }
//
//    @Bean(name = "assetOracleSqlSessionFactory")
////    @Primary
//    public SqlSessionFactory syncSqlSessionFactory(@Qualifier("assetOracleDatasource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//
//        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//
//        try {
//            factoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/sync/oracle/asset/*.xml"));
//            return factoryBean.getObject();
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean(name = "assetSqlSessionTemplate")
////    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("assetOracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
//        return sqlSessionTemplate;
//    }
//
//    @Bean(name="assetOracleTransactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("assetOracleDatasource")DataSource dataSource) throws Exception{
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
