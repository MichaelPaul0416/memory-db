package com.uft.memory.concurrency.core.listen;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public interface TaskListener<T extends ListenEvent> {

    void adviceEvent(ListenEvent listenEvent);

    List<ActionEvents> interestingEvents();
}
