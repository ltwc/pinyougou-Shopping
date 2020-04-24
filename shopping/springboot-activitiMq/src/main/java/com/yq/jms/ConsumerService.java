package com.yq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class ConsumerService {

    @JmsListener(destination = JmsConfig.TOPIC,containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(String text){
        System.out.println("topic2的消息"+text);
    }
    @JmsListener(destination = JmsConfig.TOPIC,containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage2(Message message){

        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("topic2的消息"+text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = JmsConfig.QUEUE,containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String text){
        System.out.println("queue的消息"+text);
    }

    @JmsListener(destination = JmsConfig.QUEUE,containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage2(Message message){

        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Queue2的消息"+text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
