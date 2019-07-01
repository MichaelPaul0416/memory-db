package com.paul.memory.concurrency.core;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public class RabbitObject {

    private Exchange exchange;

    private Queue queue;

    private Binding binding;

    private int batch;

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public RabbitObject() {
    }

    public RabbitObject(Exchange exchange, Queue queue, Binding binding) {

        this.exchange = exchange;
        this.queue = queue;
        this.binding = binding;
    }

    public Exchange getExchange() {

        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }
}
