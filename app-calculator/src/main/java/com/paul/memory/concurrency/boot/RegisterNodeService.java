package com.paul.memory.concurrency.boot;

import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.config.TaskConfig;
import com.paul.memory.concurrency.dao.h2.RegisterNodeDao;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import com.paul.memory.concurrency.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:当前节点注册信息到h2数据库
 * @Resource:
 */
@Component
public class RegisterNodeService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskConfig taskConfig;

    @Autowired
    private RegisterNodeDao nodeDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //注册自己节点的信息到h2

        String nodeTask = taskConfig.getModule_table();
        if(StringUtils.isEmpty(nodeTask) || !nodeTask.contains(":")){
            throw new IllegalArgumentException("wrong config of property[memory.module_table],and it's right config-->[nodeName:tableName]");
        }

        String[] infos = nodeTask.split("\\:");
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeId(CommonUtils.randomId(infos[0]));
        nodeInfo.setNodeName(infos[0]);
        nodeInfo.setTableName(infos[1]);

        logger.info("register node info:{}",nodeInfo);

        if(MemoryConstant.STRING_TRUE.equals(taskConfig.getEnableAsynWrite())){
            logger.info("负责写入的节点清除注册表数据...");
            nodeDao.truncateRegistry();
        }

        nodeDao.insertCurrentNode(nodeInfo);
        logger.info("node:{} register successfully",nodeInfo);


        logger.info("node:{} register to context",nodeInfo);
        CommonUtils.setNodeInfo(nodeInfo);
    }
}
