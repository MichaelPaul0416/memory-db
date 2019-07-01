package com.paul.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
@Configuration
@ConfigurationProperties(prefix = "common.config")
public class CommonConfig {

    private String poolCore;

    private String poolMaxSize;

    private String poolPreName;

    private String poolQueuedSize;

    public String getPoolCore() {
        return poolCore;
    }

    public void setPoolCore(String poolCore) {
        this.poolCore = poolCore;
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

    public String getPoolQueuedSize() {
        return poolQueuedSize;
    }

    public void setPoolQueuedSize(String poolQueuedSize) {
        this.poolQueuedSize = poolQueuedSize;
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
}
