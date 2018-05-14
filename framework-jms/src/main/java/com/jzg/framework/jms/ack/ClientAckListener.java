package com.jzg.framework.jms.ack;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.*;

/**
 * 消费者，不使用JmsTemplate，
 */
public class ClientAckListener implements MessageListener {

    public static final int DEFAULT_SESSION_ACKNOWLEDGE = Session.CLIENT_ACKNOWLEDGE;
    private Destination destination;
    private ConnectionFactory connectionFactory;
    private String clientId;

    private int sessionAcknowledgeMode = DEFAULT_SESSION_ACKNOWLEDGE;
    private Connection connection;
    private Session session;

    public ClientAckListener(Destination destination, ConnectionFactory connectionFactory, String clientId) {
        this.connectionFactory = connectionFactory;
        this.destination = destination;
        this.clientId = clientId;

        init();
    }

    private void init() {
        if (this.connectionFactory == null || this.destination == null) {
            throw new NullPointerException();
        }

        try {
            connection = this.connectionFactory.createConnection();
            session = connection.createSession(false, this.sessionAcknowledgeMode);

            DefaultMessageListenerContainer dmc = new DefaultMessageListenerContainer();

            dmc.setPubSubDomain(true);
            dmc.setDestination(destination);
            dmc.setConnectionFactory(this.connectionFactory);

            dmc.setPubSubNoLocal(true);
            dmc.setMessageListener(this);
            dmc.setSessionAcknowledgeMode(this.sessionAcknowledgeMode);
            dmc.setSubscriptionDurable(true);
            dmc.setCacheLevel(3);
            dmc.setClientId(clientId);

            dmc.initialize();
            dmc.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMsg = (TextMessage) message;
            try {
                System.out.println("receive message:" + textMsg.getText());
                if (session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE || session.getAcknowledgeMode() == 4) {
                    message.acknowledge();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
