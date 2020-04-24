package com.online.shopping.activeMQ;

import com.online.shopping.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class PageListener {
    @Autowired
    private ItemPageService itemPageService;

    @JmsListener(destination = JmsConfig.TOPIC_HTML,containerFactory = "jmsListenerContainerTopic")
    public void onPageCreated(Message message){
        TextMessage textMessage = (TextMessage) message;
        try{
            String text = textMessage.getText();
            System.out.println("text"+text);
            boolean b = itemPageService.genItemHtml(Long.parseLong(text));
            if (b){
                System.out.println("生成网页成功");
            }else {
                System.out.println("生成网页失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @JmsListener(destination = JmsConfig.TOPIC_HTML_DELETE,containerFactory = "jmsListenerContainerTopic")
//    public void onDeletePage(Message message){
//        TextMessage textMessage = (TextMessage) message;
//        try{
//            String text = textMessage.getText();
//            System.out.println("text"+text);
//            boolean b = itemPageService.genItemHtml(Long.parseLong(text));
//            System.out.println("生成网页成功");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
