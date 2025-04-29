package br.com.meta.controlles;

import br.com.meta.models.Visitor;
import br.com.meta.services.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Visitates", description = "CRUD de Visitantes")
@RequestMapping("/visitors")
public class VisitorController {

    @Autowired
    private VisitorService service;

    @GetMapping
    @Operation(summary = "Lista de visitantes", description = "Buscar a lista de visitantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição informada"),
            @ApiResponse(responseCode = "405", description = "Parâmetro não identificado"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = " Erro Interno do Servidor"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<List<Visitor>> findAll(@Parameter(description = "número da página") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @Parameter(description = "quantidade por página") @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        List<Visitor> list = service.findAll(page,limit);
        return  ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar um visitantes", description = "Buscar um visitante por identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição informada"),
            @ApiResponse(responseCode = "405", description = "Parâmetro não identificado"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = " Erro Interno do Servidor"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Optional<Visitor>> findById(@Parameter(description = "identificador") @PathVariable("id") String id){
        Optional<Visitor> obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Adicionar um visitante", description = "Adicionar um visitante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição informada"),
            @ApiResponse(responseCode = "405", description = "Parâmetro não identificado"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = " Erro Interno do Servidor"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Visitor> save(@Parameter(description = "dados do visitante") @RequestBody Visitor visitor){
        Visitor obj = service.save(visitor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Editar um visitante", description = "Editar um visitante pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição informada"),
            @ApiResponse(responseCode = "405", description = "Parâmetro não identificado"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = " Erro Interno do Servidor"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Visitor> update(
            @Parameter(description = "identificador") @PathVariable("id") String id,
            @Parameter(description = "dados visiante") @RequestBody Visitor visitor){
        Visitor obj = service.update(id, visitor);
        return ResponseEntity.ok().body(obj);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir um visitante", description = "Excluir um visitante pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição informada"),
            @ApiResponse(responseCode = "405", description = "Parâmetro não identificado"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = " Erro Interno do Servidor"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public void deleteById(@Parameter(description = "identificador") @PathVariable("id") String id){
        service.delete(id);
    }

}
