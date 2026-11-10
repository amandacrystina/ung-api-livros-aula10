package br.com.aula.dto;

import jakarta.validation.constraints.NotNull;

public record PedidoSaidaDto(
    @NotNull Long id,
    @NotNull LivroSaidaDto livro,
    @NotNull int quantidade
) {}