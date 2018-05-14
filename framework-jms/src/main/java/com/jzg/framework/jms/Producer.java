package com.jzg.framework.jms;

import javax.jms.Destination;

/**
 * Producer
 */
public interface Producer {
    /**
     * 发送消息
     * @param destination 目的地
     * @param msg 消息
     */
    void send(Destination destination, String msg);

    /**
     *
     * @param destinationName 目的地名称
     * @param destinationType 目的地类型  0=Queue  1=Topic
     * @param msg 消息
     */
    void send(String destinationName, int destinationType, String msg);
}
