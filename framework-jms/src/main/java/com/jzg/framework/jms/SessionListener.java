package com.jzg.framework.jms;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 监听者，支持消息回复
 */
public class SessionListener implements SessionAwareMessageListener {
    private JmsCallBack jmsCallback;

    public SessionListener(JmsCallBack jmsCallback) {
        this.jmsCallback = jmsCallback;
    }

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
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
