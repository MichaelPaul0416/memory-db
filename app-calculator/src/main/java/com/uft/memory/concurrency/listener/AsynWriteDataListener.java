package com.uft.memory.concurrency.listener;

import com.uft.memory.concurrency.core.FinalDataContainer;
import com.uft.memory.concurrency.core.UniqueId;
import com.uft.memory.concurrency.core.listen.ActionEvents;
import com.uft.memory.concurrency.core.listen.ListenEvent;
import com.uft.memory.concurrency.core.listen.TaskListener;
import com.uft.memory.concurrency.task.AsynWriteFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        //从FinalDataContainer中不断拉取数据，通过UniqueId校验数据完整性，完整的数据进行组装并且最终写入到文件中

        //写的节点，操作的module必然不是通过mq存储的，所以以这个里面的uniqueId为准
        Map<UniqueId,Boolean> currentUniqueIds = FinalDataContainer.currentAllDataKeys();


    }

    @Override
    public List<ActionEvents> interestingEvents() {
        return Arrays.asList(new ActionEvents[]{ActionEvents.ASYNC_WRITE});
    }
}
