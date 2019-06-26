package com.uft.base.worker.task;

import com.uft.base.worker.DataWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
@Component
public class BackScheduleTasker implements ApplicationListener<ContextRefreshedEvent>,DisposableBean {

    @Resource
    private ExecutorService asynThreadPool;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final List<DataWorker> dataWorkers = new ArrayList<>();


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {

            String[] workers = applicationContext.getBeanNamesForType(DataWorker.class);
            if(workers.length <= 0){
                throw new RuntimeException("none data worker["+DataWorker.class.getName()+"] is registered...");
            }

            for(String worker : workers){
                dataWorkers.add((DataWorker) applicationContext.getBean(worker));
            }

            for(DataWorker worker : dataWorkers) {
                asynThreadPool.execute(() -> {
                    try {
                        //该方法实现不同模块的数据同步，一个模块可以是一个线程去执行
                        worker.doWorker();
                    } catch (Throwable e) {
                        logger.error("数据同步到h2发生异常:" + e.getMessage(), e);
                    }

                });
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        logger.info("开始销毁bean，先关闭线程池...");

        if(!asynThreadPool.isShutdown()){
            asynThreadPool.shutdown();
        }
    }
}
