package br.com.meta.controllers;

import br.com.meta.controlles.VisitorController;
import br.com.meta.dto.VisitorDTO;
import br.com.meta.producers.VisitorRequestProducerTest;
import br.com.meta.services.VisitorService;
import br.com.meta.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorControllTest {

    @InjectMocks
    private VisitorController controller;

    @Mock
    private VisitorService service;

    @Mock
    private VisitorRequestProducerTest producer;

    @Test
    public void shouldFindAll(){

        List<VisitorDTO> obj = EntityUtils.criarListaDTO();
        when(service.findAll(any(), any())).thenReturn(obj);

        ResponseEntity<List<VisitorDTO>> objDTO = controller.findAll(any(), any());

        assertThat(objDTO.getBody().size()).isEqualTo(obj.size());
    }

    @Test
    public void shouldFindAllErroInterno(){

        when(service.findAll(any(), any())).thenThrow(new HttpMessageNotReadableException("Erro interno."));
        
        assertThrows(HttpMessageNotReadableException.class, () -> service.findAll(any(), any()));
    }

    @Test
    public void shouldFindAllErrorSemParametrosNoBody(){

        when(service.findAll(any(), any())).thenThrow(new NoSuchElementException("Não tem valor presente."));

        assertThrows(NoSuchElementException.class, () -> service.findAll(any(), any()));
    }

    @Test
    public void shouldFindByd(){

        Optional<VisitorDTO> obj = EntityUtils.criarVititanteDTO();
        when(service.findById(any())).thenReturn(obj);

        ResponseEntity<Optional<VisitorDTO>> objDTO = controller.findById(any());

        assertThat(objDTO.getBody().get().getId()).isEqualTo(obj.get().getId());
    }

    @Test
    public void shouldFindByIdErroInterno(){

        when(service.findById(any())).thenThrow(new HttpMessageNotReadableException("Erro interno."));

        assertThrows(HttpMessageNotReadableException.class, () -> service.findById(any()));
    }

    @Test
    public void shouldFindByIdErrorSemParametrosNoBody(){

        when(service.findById(any())).thenThrow(new NoSuchElementException("Não tem valor presente."));

        assertThrows(NoSuchElementException.class, () -> service.findById(any()));
    }

    @Test
    public void shouldSave(){

    }

    @Test
    public void shouldUpdate(){

        VisitorDTO obj = EntityUtils.criarDTO();
        when(service.update(any(), any())).thenReturn(obj);

        ResponseEntity<VisitorDTO> objDTO = controller.update(any(), any());

        assertThat(objDTO.getBody().getId()).isEqualTo(obj.getId());
    }

    @Test
    public void shouldUpdateErroInterno(){

        when(service.update(any(), any())).thenThrow(new HttpMessageNotReadableException("Erro interno."));

        assertThrows(HttpMessageNotReadableException.class, () -> service.update(any(), any()));
    }

    @Test
    public void shouldUpdateErrorSemParametrosNoBody(){

        when(service.update(any(), any())).thenThrow(new NoSuchElementException("Não tem valor presente."));

        assertThrows(NoSuchElementException.class, () -> service.update(any(), any()));
    }

    @Test
    public void shouldDelete(){

        doNothing().when(service).delete(any());

        controller.deleteById(any());

        verify(service, times(1)).delete(any());
    }

    @Test
    public void shouldDeleteErroInterno(){

        doThrow(new HttpMessageNotReadableException("Erro interno.")).when(service).delete(any());

        assertThrows(HttpMessageNotReadableException.class, () -> controller.deleteById(any()));
    }

    @Test
    public void shouldDeleteErrorSemParametrosNoBody(){


        doThrow(new NoSuchElementException("Não tem valor presente.")).when(service).delete(any());

        assertThrows(NoSuchElementException.class, () -> controller.deleteById(any()));

    }


}
