package br.com.meta.services;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.exception.utils.VisitorException;
import br.com.meta.models.Visitor;
import br.com.meta.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository repository;

    public List<VisitorDTO> findAll(Integer page, Integer limit) throws NoSuchElementException, HttpMessageNotReadableException{
            List<Visitor> list = repository.findAll(PageRequest.of(page, limit));
            List<VisitorDTO> listDTO;
            listDTO = list.stream().map(VisitorDTO::new).collect(Collectors.toList());
            return listDTO;
    }

    @Cacheable(value = "visitors_cache", key="#id")
    public VisitorDTO findById(String id) throws NoSuchElementException, HttpMessageNotReadableException{

        Visitor visitor = repository.findById(id).orElseThrow(() -> new VisitorException(id));
        return new VisitorDTO(visitor);
    }

    public void delete(String id) throws NoSuchElementException, HttpMessageNotReadableException{
        repository.findById(id).orElseThrow(() -> new VisitorException(id));
        repository.deleteById(id);
    }

    @CacheEvict(value = "visitors_cache", key="#id")
    public VisitorDTO update(String id , VisitorDTO obj) throws NoSuchElementException, HttpMessageNotReadableException {

        checkVisitor(obj);

        Visitor entity = repository.findById(id).orElseThrow(() -> new VisitorException(id));
        updateData(entity, obj);
        entity = repository.save(entity);
        VisitorDTO visitorDTO;
        visitorDTO = new VisitorDTO(entity);
        return visitorDTO;
    }

    private void updateData(Visitor now, VisitorDTO others) {
        now.setName(others.getName().trim());
        now.setDescription(others.getDescription());
    }

    private void checkVisitor(VisitorDTO dto){
        if(dto.getName().trim().isEmpty()){
            throw new NoSuchElementException("Nome não existe.");
        }
    }
}

