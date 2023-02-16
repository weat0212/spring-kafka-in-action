package com.andywang.jms.consumer;

import com.andywang.jms.config.KafkaTopicConfig;
import com.andywang.jms.producer.KafkaProducerDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageReceiver {

    @KafkaListener(id = "my", topics = KafkaProducerDemo.MY_TOPIC)
    public void listenMyTopic(String in) {
        log.info("Message Received: " + in);
    }

    @KafkaListener(id = "andy", topics = KafkaTopicConfig.INTEREST_TOPIC)
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + " from partition: " + partition);
    }
}
