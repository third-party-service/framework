package com.jzg.framework.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * 生产者，使用JmsTemplate，仅支持发送文本消息TextMessage
 */
@Component("defaultProducer")
public class DefaultProducer implements Producer {

    @Resource
    private JmsTemplate jmsTemplate;


    /**
     * 发送消息
     *
     * @param msg
     */
    @Override
    public void send(Destination destination, final String msg) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                System.out.println("*****************");

                TextMessage textMessage = session.createTextMessage(msg);
                System.out.println("send text message: " + textMessage.getText());

                return textMessage;
            }
        });
    }

    /**
     * @param destinationName 目的地名称
    * @param destinationType 目的地类型  0=Queue  1=Topic
     * @param msg             消息
     */
    @Override
    public void send(String destinationName, int destinationType, final String msg) {
        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
