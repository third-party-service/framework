package com.jzg.framework.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 默认监听器，只支持消息自动签收
 *
 * 支持Session.AUTO_ACKNOWLEDGE 1 消息自动签收
 * 若需要Session.CLIENT_ACKNOWLEDGE 2，请使用ActiveMQSubscriber
 *
 */
public class DefaultListener implements MessageListener {
    private JmsCallBack jmsCallback;

    public DefaultListener(JmsCallBack jmsCallback) {
        this.jmsCallback = jmsCallback;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage textMsg = (TextMessage) message;
                boolean bRet = this.jmsCallback.run(textMsg.getText());

                System.out.println("receive message:" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


}
