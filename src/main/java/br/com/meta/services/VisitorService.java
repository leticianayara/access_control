package br.com.meta.services;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.models.Visitor;
import br.com.meta.producers.VisitorRequestProducer;
import br.com.meta.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository repository;

    @Autowired
    private VisitorRequestProducer visitorRequestProducer;

    public List<VisitorDTO> findAll(Integer page, Integer limit){
            List<Visitor> list = repository.findAll(PageRequest.of(page, limit));
            List<VisitorDTO> listDTO;
            listDTO = list.stream().map(VisitorDTO::new).collect(Collectors.toList());
            return listDTO;
    }

    @Cacheable(value = "visitors_cache", key="#id")
    public Optional<VisitorDTO> findById(String id){

        Optional<Visitor> visitor = repository.findById(id);
        Optional<VisitorDTO> visitorDTO;
        visitorDTO = visitor.stream().map(VisitorDTO::new).findFirst();
        return visitorDTO;
    }

    public void delete(String id){
        repository.deleteById(id);
    }

    @CacheEvict(value = "visitors_cache", key="#id")
    public VisitorDTO update(String id , VisitorDTO obj) {

        Visitor entity = repository.findById(id).orElseThrow();
        updateData(entity, obj);
        entity = repository.save(entity);
        VisitorDTO visitorDTO;
        visitorDTO = new VisitorDTO(entity);
        return visitorDTO;
    }

    private void updateData(Visitor now, VisitorDTO others) {
        now.setName(others.getName());
        now.setDescription(others.getDescription());
    }
}

