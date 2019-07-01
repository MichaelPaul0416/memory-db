package com.paul.memory.concurrency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@ConfigurationProperties(prefix = "memory")
@Component
public class TaskConfig {

    private String modules;

    private String account_number;
    private String trade_number;
    private String order_number;

    private String poolCore;
    private String poolQueuedSize;
    private String poolMaxSize;
    private String poolPreName;

    private String module_table;

    public String getModule_table() {
        return module_table;
    }

    public void setModule_table(String module_table) {
        this.module_table = module_table;
    }

    private String enableAsynWrite;

    public String getEnableAsynWrite() {
        return enableAsynWrite;
    }

    public void setEnableAsynWrite(String enableAsynWrite) {
        this.enableAsynWrite = enableAsynWrite;
    }

    public String getPoolCore() {
        return poolCore;
    }

    public void setPoolCore(String poolCore) {
        this.poolCore = poolCore;
    }

    public String getPoolQueuedSize() {
        return poolQueuedSize;
    }

    public void setPoolQueuedSize(String poolQueuedSize) {
        this.poolQueuedSize = poolQueuedSize;
    }

    public String getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(String poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

    public String getPoolPreName() {
        return poolPreName;
    }

    public void setPoolPreName(String poolPreName) {
        this.poolPreName = poolPreName;
    }

    @Bean(name="asynThreadPool")
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                Integer.parseInt(poolCore),
                Integer.parseInt(poolMaxSize),
                1000 * 60,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(Integer.parseInt(poolQueuedSize)),
                new ThreadFactory() {
                    final SecurityManager manager = System.getSecurityManager();
                    final ThreadGroup threadGroup = manager == null ? Thread.currentThread().getThreadGroup() : manager.getThreadGroup();
                    final AtomicInteger counter = new AtomicInteger(0);
                    final String prefix = poolPreName + "-";

                    @Override
                    public Thread newThread(Runnable r) {

                        Thread thread = new Thread(threadGroup,r,prefix + counter.getAndAdd(1),0);

                        if(thread.isDaemon()){
                            thread.setDaemon(false);
                        }
                        if (thread.getPriority() != Thread.NORM_PRIORITY) {
                            thread.setPriority(Thread.NORM_PRIORITY);
                        }
                        return thread;
                    }
                }

        );

        return threadPoolExecutor;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getTrade_number() {
        return trade_number;
    }

    public void setTrade_number(String trade_number) {
        this.trade_number = trade_number;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}
