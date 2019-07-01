package com.paul.memory.concurrency.core.listen;

import com.paul.memory.concurrency.utils.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class TaskListenerRegistry implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final List<TaskListener> listeners = new ArrayList<>(4);

    @Autowired
    private ApplicationUtils applicationUtils;


    @Override
    public void afterPropertiesSet() throws Exception {
        Set<TaskListener> set = applicationUtils.getBeansByType(TaskListener.class);
        listeners.addAll(set);
    }

    /**
     *
     * @author:wangqiang20995
     * @datetime:2019/6/27 13:31
     * @param: [listenEvent]
     * @description:广播触发的事件
     * @return: void
     *
     **/
    public void broadcastEvent(ListenEvent listenEvent){
        logger.info("开始广播事件[{}]",listenEvent.eventType());
        for(TaskListener taskListener : listeners){
            List<ActionEvents> list = taskListener.interestingEvents();
            //获得对当前事件感兴趣的listener
            if(list.contains(listenEvent.eventType())){
                taskListener.adviceEvent(listenEvent);
            }
        }
    }
}
