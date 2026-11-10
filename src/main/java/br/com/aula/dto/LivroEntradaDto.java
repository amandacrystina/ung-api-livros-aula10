package br.com.aula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroEntradaDto(
    @NotBlank String titulo,
    @NotNull int ano,
    @NotNull Long autorId
) {}