package br.com.meta.producers;

import br.com.meta.config.ConfigureRabbitMq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VisitorRequestProducer {

    private final RabbitTemplate rabbitTemplate;

    public VisitorRequestProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String produceMessage(String message) {
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "crud-request-rout-key.messages",
                message);
        return "Message(" + message + ")" + " has been produced.";
    }
}
