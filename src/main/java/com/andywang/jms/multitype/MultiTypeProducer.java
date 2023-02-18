package com.andywang.jms.multitype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultiTypeProducer {

    @Autowired
    @Qualifier(value = "multiTypeKafkaTemplate")
    private KafkaTemplate<String, Object> multiTypeKafkaTemplate;

    public static final String MULTI_TYPE_TOPIC = "multi-type-topic";

    public void sendMultiTypeMessage() {
        multiTypeKafkaTemplate.send(MULTI_TYPE_TOPIC, TypeOne.builder().message("Message from Type 1").timestamp(new Date()).build());
        multiTypeKafkaTemplate.send(MULTI_TYPE_TOPIC, TypeTwo.builder().message("Message from Type 2").timestamp(new Date()).build());
        multiTypeKafkaTemplate.send(MULTI_TYPE_TOPIC, "Plain Text Message");
    }
}
