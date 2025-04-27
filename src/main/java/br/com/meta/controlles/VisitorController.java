package br.com.meta.controlles;

import br.com.meta.models.Visitor;
import br.com.meta.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visitors")
public class VisitorController {

    @Autowired
    private VisitorService service;

    @GetMapping
    public ResponseEntity<List<Visitor>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        List<Visitor> list = service.findAll(page,limit);
        return  ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Visitor>> findById(@PathVariable("id") String id){
        Optional<Visitor> obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Visitor> save(@RequestBody Visitor visitor){
        Visitor obj = service.save(visitor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Visitor> update(@PathVariable("id") String id, @RequestBody Visitor visitor){
        Visitor obj = service.update(id, visitor);
        return ResponseEntity.ok().body(obj);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") String id){
        service.delete(id);
    }

}
