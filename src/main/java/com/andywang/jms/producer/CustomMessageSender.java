package com.andywang.jms.producer;

import com.andywang.jms.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomMessageSender {

    public static final String CUSTOM_TOPIC = "custom-topic";

    @Autowired
    @Qualifier("customObjectKafkaTemplate")
    private KafkaTemplate<String, CustomMessage> kafkaTemplate;

    public void sendCustomMessage(CustomMessage customMessage) {
        log.info("Send Custom Message: " + customMessage.toString());
        kafkaTemplate.send(CUSTOM_TOPIC, customMessage);
    }
}
