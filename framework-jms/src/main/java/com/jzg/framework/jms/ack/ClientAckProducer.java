package com.jzg.framework.jms.ack;

import com.jzg.framework.jms.Producer;

import javax.jms.*;

public class ClientAckProducer implements Producer {

    public static final int DEFAULT_SESSION_ACKNOWLEDGE = Session.CLIENT_ACKNOWLEDGE;
    private Destination destination;
    private ConnectionFactory connectionFactory;
    private int sessionAcknowledgeMode = DEFAULT_SESSION_ACKNOWLEDGE;



    public ClientAckProducer(Destination destination, ConnectionFactory connectionFactory) {
        this.destination = destination;
        this.connectionFactory = connectionFactory;
    }


    /**
     * 发送消息
     *
     * @param msg
     */
    @Override
    public void send(Destination destination, String msg) {
        if (connectionFactory == null || destination == null) {
            throw new NullPointerException();
        }

        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, this.sessionAcknowledgeMode);
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(msg);
            producer.send(textMessage);
            System.out.println("send text message: " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.stop();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param destinationName 目的地名称
     * @param destinationType 目的地类型  0=Queue  1=Topic
     * @param msg             消息
     */
    @Override
    public void send(String destinationName, int destinationType, final String msg) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, this.sessionAcknowledgeMode);
            Destination destination;
            if (destinationType == 0) {
                destination = session.createQueue(destinationName);
            }else {
                destination = session.createTopic(destinationName);
            }
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(msg);
            producer.send(textMessage);


        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.stop();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
