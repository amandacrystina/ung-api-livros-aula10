package br.com.aula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroSaidaDto(
    @NotNull Long id,
    @NotBlank String titulo,
    @NotNull int ano,
    @NotNull AutorSaidaDto autor
) {}