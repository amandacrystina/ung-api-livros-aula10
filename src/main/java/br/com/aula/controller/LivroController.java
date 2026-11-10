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

import br.com.aula.dto.LivroEntradaDto;
import br.com.aula.dto.LivroSaidaDto;
import br.com.aula.service.LivroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService service) {
        this.livroService = service;
    }

    @Operation(
        summary = "Listar livro cadastrados", 
        description = "Retorna uma lista com os dados dos livros cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livros encontrados"),
        @ApiResponse(responseCode = "404", description = "Livros não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<LivroSaidaDto>> getLivros() {
        List<LivroSaidaDto> livros = this.livroService.ListarLivros();
        return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @Operation(
        summary = "Consultar livro por identificador", 
        description = "Retorna os dados do livro solicitado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroSaidaDto> getLivroById(@PathVariable Long id) {
        LivroSaidaDto livro = this.livroService.ConsultarLivro(id);
        return ResponseEntity.status(HttpStatus.OK).body(livro);
    }

    @Operation(
        summary = "Cadastrar um novo livro", 
        description = "Registra os dados de um novo livro no sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<LivroSaidaDto> cadastrar(@Valid @RequestBody LivroEntradaDto dto) {
        LivroSaidaDto livro = livroService.Cadastrar(dto);
        URI location = URI.create("/api/livros/" + livro.id());
        return ResponseEntity.created(location).body(livro);
    }
    @Operation(summary = "Consultar livros por autor", description = "Retorna todos os livros de um determinado autor")
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<LivroSaidaDto>> getLivrosByAutor(@PathVariable Long autorId) {
        List<LivroSaidaDto> livros = this.livroService.listarPorAutor(autorId);
        return ResponseEntity.ok(livros);
    }
}