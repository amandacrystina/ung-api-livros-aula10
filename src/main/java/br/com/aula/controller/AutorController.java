package br.com.aula.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aula.dto.AutorEntradaDto;
import br.com.aula.dto.AutorSaidaDto;
import br.com.aula.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @Operation(summary = "Listar autores", description = "Retorna todos os autores cadastrados")
    @GetMapping
    public ResponseEntity<List<AutorSaidaDto>> getAutores() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @Operation(summary = "Consultar autor por ID", description = "Retorna os dados de um autor especifico")
    @GetMapping("/{id}")
    public ResponseEntity<AutorSaidaDto> getAutorById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo autor", description = "Registra um novo autor no sistema")
    @PostMapping
    public ResponseEntity<AutorSaidaDto> cadastrar(@Valid @RequestBody AutorEntradaDto dto) {
        AutorSaidaDto autor = autorService.cadastrar(dto);
        URI location = URI.create("/api/autores/" + autor.id());
        return ResponseEntity.created(location).body(autor);
    }
}