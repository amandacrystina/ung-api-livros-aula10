package br.com.aula.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aula.dto.PedidoEntradaDto;
import br.com.aula.dto.PedidoSaidaDto;
import br.com.aula.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Listar pedidos de compra", description = "Retorna todos os pedidos de compra de livros cadastrados")
    @GetMapping
    public ResponseEntity<List<PedidoSaidaDto>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @Operation(summary = "Cadastrar pedido de compra", description = "Registra a compra de um livro com quantidade")
    @PostMapping
    public ResponseEntity<PedidoSaidaDto> cadastrar(@Valid @RequestBody PedidoEntradaDto dto) {
        PedidoSaidaDto pedido = pedidoService.cadastrar(dto);
        URI location = URI.create("/api/pedidos/" + pedido.id());
        return ResponseEntity.created(location).body(pedido);
    }
}