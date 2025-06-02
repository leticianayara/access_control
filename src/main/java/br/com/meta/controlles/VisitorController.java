package br.com.meta.controlles;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.producers.VisitorRequestProducer;
import br.com.meta.services.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@Tag(name = "Visitates", description = "CRUD de Visitantes")
@RequestMapping("/visitors")
public class VisitorController {

    @Autowired
    private VisitorService service;

    @Autowired
    private VisitorRequestProducer produceMessageService;

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
    public ResponseEntity<List<VisitorDTO>> findAll(@Parameter(description = "número da página") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @Parameter(description = "quantidade por página") @RequestParam(value = "limit", defaultValue = "50q") Integer limit) {
        try {
            List<VisitorDTO> list = null;
            list = service.findAll(page, limit);
            return ResponseEntity.ok().body(list);
        }catch (MethodArgumentTypeMismatchException ex){
            throw new RuntimeException();
        }

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
    public ResponseEntity<VisitorDTO> findById(@Parameter(description = "identificador") @PathVariable("id") String id){
        try{
            VisitorDTO obj = service.findById(id);
            return ResponseEntity.ok().body(obj);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Não tem valor presente.");
        } catch (HttpMessageNotReadableException e){
            throw new HttpMessageNotReadableException("Erro interno.");
        }
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
    public String save(@Parameter(description = "dados do visitante") @RequestBody String visitor) {
        return produceMessageService.produceMessage(visitor);
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
    public ResponseEntity<VisitorDTO> update(
            @Parameter(description = "identificador") @PathVariable("id") String id,
            @Parameter(description = "dados visiante") @RequestBody VisitorDTO visitorDTO){
        try {

            VisitorDTO obj = service.update(id, visitorDTO);
            return ResponseEntity.ok().body(obj);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Não tem valor presente.");
        } catch (HttpMessageNotReadableException e){
            throw new HttpMessageNotReadableException("Erro interno.");
        }
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
