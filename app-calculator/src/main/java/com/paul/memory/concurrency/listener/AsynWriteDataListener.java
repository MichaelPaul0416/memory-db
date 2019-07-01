package com.paul.memory.concurrency.listener;

import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.core.FinalDataContainer;
import com.paul.memory.concurrency.core.UniqueId;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.core.listen.ActionEvents;
import com.paul.memory.concurrency.core.listen.ListenEvent;
import com.paul.memory.concurrency.core.listen.TaskListener;
import com.paul.memory.concurrency.dao.h2.RegisterNodeDao;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import com.paul.memory.concurrency.task.AsynWriteFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class AsynWriteDataListener implements TaskListener<AsynWriteFile> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RegisterNodeDao registerNodeDao;

    @Override
    public void adviceEvent(ListenEvent listenEvent) {
        logger.info("异步写入数据监听器[{}]开始获取并写入完整数据",this.getClass().getName());

        //先加载所有节点的注册信息
        List<NodeInfo> allNodes = registerNodeDao.timedAllNodesBatch();
        if(allNodes == null || allNodes.size() == 0){
            throw new IllegalStateException("none calculator node is registered,please check in");
        }

        //从FinalDataContainer中不断拉取数据，通过UniqueId校验数据完整性，完整的数据进行组装并且最终写入到文件中
        //写的节点，操作的module必然不是通过mq存储的，所以以这个里面的uniqueId为准

        singleModuleWrite(allNodes);


    }

    /**
     * 写入单模块的数据到数据库中
     * @param allNodes
     */
    private void singleModuleWrite(List<NodeInfo> allNodes) {
        long start = System.currentTimeMillis();
        while (true) {
            Iterator<Map.Entry<UniqueId,AbstractData>> iterator = FinalDataContainer.moduleData(MemoryConstant.MODULE_ACCOUNT).entrySet().iterator();
            List<AbstractData> list = new ArrayList<>();

            while (iterator.hasNext()){
                Map.Entry<UniqueId,AbstractData> entry = iterator.next();
                UniqueId uniqueId = entry.getKey();
                //多模块的话，需要各个模块校验当前UniqueId关联的数据是否都到了，单模块的话，直接取就行
                AbstractData abstractData = FinalDataContainer.getAndRemoveModuleData(uniqueId);

//                if(FinalDataContainer.dataComplete(uniqueId,false,MemoryConstant.MODULE_ACCOUNT)){
//                    abstractData = FinalDataContainer.getAndRemoveModulesData(uniqueId);
//                    判断当前这条数据是否是这个模块的最后一条
//                    accountData标记为最后一条，FinalDataContainer中对应模块没有数据
//                    获取dealNode，然后加入complete
//                }

                //将这一批次的数据或者达到一批次阀值之后组装成list，批量插入oracle
                list.add(abstractData);

                if(list.size() >= MemoryConstant.BATCH_MAX_SIZE){
                    logger.info("本批次的数据已经达到阀值[{}]，先插入数据，后继续读取",MemoryConstant.BATCH_MAX_SIZE);
                    break;
                }

            }

            writeToTarget(list);

            //暂时只对Account这个模块判断
            if(FinalDataContainer.isSingleModuleDone(allNodes.size())
                    && FinalDataContainer.emptyModuleData(MemoryConstant.MODULE_ACCOUNT)){
                logger.info("本次数据全部写入完毕，即将退出...");
                break;
            }

            long end = System.currentTimeMillis();
            if(end - start > (1000 * 60 * 10)){
                logger.warn("接受数据超时,本次直接退出...");
                break;
            }

        }
    }

    @Override
    public List<ActionEvents> interestingEvents() {
        return Arrays.asList(new ActionEvents[]{ActionEvents.ASYNC_WRITE});
    }

    private void writeToTarget(List<AbstractData> batchData){
        if(batchData.size() == 0){
            return;
        }
        // write data to database
        logger.info("写入数据库[{}]条数据",batchData.size());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
