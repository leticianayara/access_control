package br.com.meta.services;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.models.Visitor;
import br.com.meta.producers.VisitorRequestProducerTest;
import br.com.meta.repositories.VisitorRepository;
import br.com.meta.utils.EntityUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static  org.assertj.core.api.Assertions.assertThat;
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
        when(repository.findById(ArgumentMatchers.any())).thenReturn(EntityUtils.criarVititante());

        Optional<VisitorDTO> obj = service.findById(ArgumentMatchers.any());

        assertThat(obj.get().getId()).isEqualTo((EntityUtils.criarVititanteDTO()).get().getId());
    }
    
    @Test
    public void shouldDeleteSuccess(){
        doNothing().when(repository).deleteById(any());

        service.delete(any());

        verify(repository, times(1)).deleteById(any());
    }

    @Test
    public void shouldUpdateSuccess(){
        Visitor obj = EntityUtils.criar();
        when(repository.findById(any())).thenReturn(Optional.of(obj));
        when(repository.save(any())).thenReturn(obj);

        VisitorDTO objDTO = service.update(any(), EntityUtils.criarDTO());

        assertThat(objDTO.getId()).isEqualTo((EntityUtils.criarVititanteDTO()).get().getId());
        assertEquals(EntityUtils.criarDTO().getId(),objDTO.getId());
        assertEquals(EntityUtils.criarDTO().getName(), objDTO.getName());
        verify(repository, times(1)).findById(any());
    }
}
