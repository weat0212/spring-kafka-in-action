package com.andywang.jms.consumer;

import com.andywang.jms.producer.CustomMessageSender;
import com.andywang.jms.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomMessageReceiver {

    @KafkaListener(topics = CustomMessageSender.CUSTOM_TOPIC, containerFactory = "customMessageKafkaListenerContainerFactory")
    public void greetingListener(CustomMessage message) {
        System.out.println("Custom Message Received: " + message.toString());
    }
}
