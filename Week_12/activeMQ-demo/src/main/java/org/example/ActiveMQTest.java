package org.example;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

public class ActiveMQTest {

    public static void main(String[] args) {
        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        final ActiveMQDestination queueDestination = new ActiveMQQueue("test.queue");
        final ActiveMQDestination topicDestination = new ActiveMQTopic("test.topic");
        new Thread(new Tester(queueDestination, factory)).start();
        new Thread(new Tester(topicDestination, factory)).start();
    }


}
