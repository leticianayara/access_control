package br.com.meta.consumer;

import br.com.meta.services.VisitorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class VisitorResponseErrorConsumer {

    @Autowired
    private VisitorService service;

    @RabbitListener(queues = {"crud-response-error-queue"})
    public void receive (@Payload Message message){
        String payload = String.valueOf(message.getPayload());
        service.errorCrud(payload);
    }
}
