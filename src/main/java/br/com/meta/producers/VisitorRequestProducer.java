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

        String[] strSemRegex = message.split("[\\,\\.\\{\\}]");
        String[] strSemPontos = strSemRegex[2].split(":");
        if(strSemPontos[1].trim().isEmpty() || strSemPontos[1].trim().equals("null") ){
            throw new IllegalArgumentException("Nome não pode ser nulo");
        }

        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "crud-request-rout-key.messages",
                message);
        return "Message has been produced.";
    }
}
