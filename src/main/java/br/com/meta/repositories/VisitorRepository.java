package br.com.meta.repositories;

import br.com.meta.models.Visitor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends CrudRepository<Visitor, String> {

    List<Visitor> findAll(Pageable pageable);
}