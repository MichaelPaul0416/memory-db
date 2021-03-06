package com.paul.facade;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
public class MemoryConstant {

    public static final String SUCCESS = "MEM-0000";

    public static final String MODULE_TRADE = "trade";

    public static final String MODULE_ACCOUNT = "account";

    public static final String MODULE_ORDER = "order";

    public static final String RABBITMQ_QUEUE_PREFIX = "_queue_";

    public static final String RABBITMQ_ROUTING_PREFIX = "_routing_";

    public static final String RABBIT_BINDING_PREFIX = "_binding_";

    public static final String PROJECT_CONFIG = "application.properties";

    public static final int QUEUE_WITH_PROCESSORS = 5;

    public static final int SINGLE_MODULE_CONCURRENT = Runtime.getRuntime().availableProcessors() << 1;

    public static final int MAX_QUEUE_NUMBER = 4;

    public static final String STRING_TRUE = "true";

    public static final int BATCH_MAX_SIZE = 1000;

    public static final String DONE_EXCHANGE = "nodes_exchange_done";

    public static final String DONE_QUEUE = "nodes_queue_done";

    public static final String DONE_BINDING = "nodes_routing_done";
}
