package org.example;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;

public class Tester implements Runnable{

    ActiveMQDestination destination;
    ActiveMQConnectionFactory factory;
    public Tester(final ActiveMQDestination destination, final ActiveMQConnectionFactory factory) {
        this.destination = destination;
        this.factory = factory;
    }
    public void run() {
        ActiveMQConnection conn = null;
        Session session = null;
        try {
            conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);
            MessageListener listener = new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        System.out.println(destination.getDestinationTypeAsString() + " receive message：" + ((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };
            consumer.setMessageListener(listener);
            MessageProducer producer = session.createProducer(destination);
            while (true) {
                TextMessage message = session.createTextMessage("hello");
                producer.send(message);
                Thread.sleep(2000);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
                conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
