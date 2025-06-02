package br.com.meta.access_control.services;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.exception.utils.VisitorException;
import br.com.meta.models.Visitor;
import br.com.meta.access_control.producers.VisitorRequestProducerTest;
import br.com.meta.repositories.VisitorRepository;
import br.com.meta.access_control.utils.EntityUtils;
import br.com.meta.services.VisitorService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static  org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @InjectMocks
    private VisitorService service;

    @Mock
    private VisitorRepository repository;

    @Mock
    private VisitorRequestProducerTest producer;

    @Test
    public void shouldFindAllSuccess() throws BadRequestException, ClassNotFoundException {
        when(repository.findAll(PageRequest.of(1, 5)))
                .thenReturn(EntityUtils.criarListaAllVisitantes());
        List<VisitorDTO> list = service.findAll(1, 5);

        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    public void  shouldFindByIdSuccess(){
        Visitor v = EntityUtils.criar();
        given(repository.findById(any())).willReturn(Optional.of(v));

        VisitorDTO obj = service.findById(any());

        assertThat(obj.getId()).isEqualTo((EntityUtils.criarDTO()).getId());
    }

    @Test
    public void  shouldFindByIdError(){
        Visitor v = EntityUtils.criar();
        given(repository.findById(any())).willThrow(VisitorException.class);

        assertThatThrownBy(() -> service.findById(any())).isInstanceOf(VisitorException.class);

    }
    
    @Test
    public void shouldDeleteSuccess(){

        doNothing().when(repository).deleteById(any());
        given(repository.findById(any())).willReturn(Optional.of(EntityUtils.criar()));

        service.delete(any());

        verify(repository, times(1)).deleteById(any());
    }

    @Test
    public void shouldDeleteError(){

        given(repository.findById(any())).willThrow(VisitorException.class);

        assertThatThrownBy(() -> service.delete(any())).isInstanceOf(VisitorException.class);
    }

    @Test
    public void shouldUpdateSuccess(){
        Visitor obj = EntityUtils.criar();

        given(repository.findById(any())).willReturn(Optional.of(obj));
        when(repository.save(any())).thenReturn(obj);

        VisitorDTO objDTO = service.update(any(), EntityUtils.criarDTO());

        assertThat(objDTO.getId()).isEqualTo((EntityUtils.criarDTO()).getId());
        assertEquals(EntityUtils.criarDTO().getId(),objDTO.getId());
        assertEquals(EntityUtils.criarDTO().getName(), objDTO.getName());
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void shouldUpdateErro(){

        given(repository.findById(any())).willThrow(VisitorException.class);

        assertThatThrownBy(() -> service.delete(any())).isInstanceOf(VisitorException.class);
    }
}
