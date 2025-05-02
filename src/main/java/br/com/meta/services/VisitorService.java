package br.com.meta.services;

import br.com.meta.models.Visitor;
import br.com.meta.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository repository;

    public List<Visitor> findAll(Integer page, Integer limit){
        return repository.findAll(PageRequest.of(page,limit));
    }

    @Cacheable(value = "visitors_cache", key="#id")
    public Optional<Visitor> findById(String id){
        return repository.findById(id);
    }

    public Visitor save(Visitor visitor){
        return repository.save(visitor);
    }

    public void delete(String id){
        repository.deleteById(id);
    }

    @CacheEvict(value = "visitors_cache", key="#id")
    public Visitor update(String id , Visitor obj) {
        Visitor entity = repository.findById(id).orElseThrow();
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(Visitor now, Visitor others) {
        now.setName(others.getName());
        now.setDescription(others.getDescription());
    }
}

