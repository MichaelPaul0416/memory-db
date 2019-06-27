package com.uft.memory.concurrency.listener;

import com.uft.memory.concurrency.core.listen.ActionEvents;
import com.uft.memory.concurrency.core.listen.ListenEvent;
import com.uft.memory.concurrency.core.listen.TaskListener;
import com.uft.memory.concurrency.task.AsynWriteFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class AsynWriteDataListener implements TaskListener<AsynWriteFile> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void adviceEvent(ListenEvent listenEvent) {
        logger.info("异步写入数据监听器[{}]开始获取并写入完整数据",this.getClass().getName());
    }

    @Override
    public List<ActionEvents> interestingEvents() {
        return Arrays.asList(new ActionEvents[]{ActionEvents.ASYNC_WRITE});
    }
}
