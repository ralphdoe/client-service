package com.ralphdoe.clientservice.adapters.in.messaging;

import com.ralphdoe.clientservice.adapters.out.messaging.model.ClientEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientEventListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleClientCreatedEvent(ClientEvent event) {
        log.info("Received async client event: {}", event);
        // Here you could trigger audit logging, scoring analysis, etc.
    }
}
