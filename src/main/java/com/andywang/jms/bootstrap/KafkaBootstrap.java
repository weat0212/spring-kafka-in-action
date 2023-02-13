package com.andywang.jms.bootstrap;

import com.andywang.jms.producer.KafkaProducerDemo;
import com.andywang.jms.producer.MessageSender;
import com.andywang.jms.producer.PartitionRoundRobin;
import com.andywang.jms.producer.ProducerWithCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

//    @Autowired
//    private MessageSender sender;

//    @Autowired
//    private KafkaProducerDemo kafkaProducer;

//    @Autowired
//    private ProducerWithCallback producerWithCallback;

    @Autowired
    private PartitionRoundRobin partitionRoundRobin;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        sender.sendMessage("Hello Spring Kafka");
//        kafkaProducer.sendMessage("Hello MyTopic");
//        producerWithCallback.sendMessage("Hello Callback");
        partitionRoundRobin.sendMessage("Round Robin");
    }
}
