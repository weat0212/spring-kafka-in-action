package com.andywang.jms.bootstrap;

import com.andywang.jms.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MessageSender sender;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        sender.sendMessage("Hello Spring Kafka");
    }
}
