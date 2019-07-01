package com.paul.base;

//import com.paul.base.sync.h2.dao.BookDao;
//import com.paul.base.sync.h2.model.Book;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.paul.base.sync.oracle.Pager;
import com.paul.base.sync.oracle.asset.dao.FundAccountMapper;
import com.paul.base.sync.oracle.asset.model.FundAccount;
import com.paul.base.sync.oracle.secu.dao.EntrustMapper;
import com.paul.base.sync.oracle.secu.model.Entrust;
import com.paul.base.sync.oracle.user.dao.StkCodeMapper;
import com.paul.base.sync.oracle.user.model.StkCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@SpringBootApplication
@EnableAutoConfiguration
//@EnableDubbo(scanBasePackages = "com.paul.base.service")
public class BaseServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(BaseServiceApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BaseServiceApplication.class, args);

        StkCodeMapper mapper = applicationContext.getBean(StkCodeMapper.class);
        EntrustMapper entrustMapper = applicationContext.getBean(EntrustMapper.class);
        FundAccountMapper fundAccountMapper = applicationContext.getBean(FundAccountMapper.class);

//        StkCode stkCode = new StkCode();
//        stkCode.setStockType("a");
//        logger.info("start");
//        List<StkCode> list = mapper.queryByCondition(stkCode);
//        logger.info("size:{}",list.size());

        logger.info("start");
//        Entrust entrust = new Entrust();
//        entrust.setBatchNo((long) 98);
//        List<Entrust> entrusts = entrustMapper.queryByCondition(entrust);
//        Pager pager = new Pager();
//        pager.setStart(5000);
//        List<Entrust> entrusts = entrustMapper.queryByPage(pager);

        logger.info("size:{}",entrustMapper.totalNumber());

//        logger.info("start");
//        FundAccount fundAccount = new FundAccount();
//        fundAccount.setFundAccount("220000360");
//        List<FundAccount> fundAccounts = fundAccountMapper.queryByCondition(fundAccount);
//        logger.info("size:{}",fundAccounts.size());


    }
    private static void showAllBean(ApplicationContext applicationContext){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for(String bean : beanNames){
            logger.info("bean:{}",bean);
        }
    }
}
