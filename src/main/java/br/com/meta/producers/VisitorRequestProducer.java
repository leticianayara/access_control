package br.com.meta.producers;

import br.com.meta.dto.VisitorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitorRequestProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void integrar(VisitorDTO visitorDTO) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "crud-request-exchange",
                "crud-request-rout-key",
                objectMapper.writeValueAsString(visitorDTO));
    }
}
