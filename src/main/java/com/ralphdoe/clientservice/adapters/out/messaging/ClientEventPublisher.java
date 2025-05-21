package com.ralphdoe.clientservice.adapters.out.messaging;

import com.ralphdoe.clientservice.adapters.out.messaging.model.ClientEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void publishClientCreatedEvent(ClientEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
