package br.com.aula.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoEntradaDto(
    @NotNull Long livroId,
    @NotNull @Positive int quantidade
) {}