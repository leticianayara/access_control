package br.com.meta.access_control.producers;

import br.com.meta.producers.VisitorRequestProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorRequestProducerTest {

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    VisitorRequestProducer producer;

    @Test
    public void shouldSaveSuccess(){
        rabbitTemplate.convertAndSend((Object) any(), any(), any());

        verify(rabbitTemplate, times(1)).convertAndSend((Object)any(), any(), any());
    }

}
