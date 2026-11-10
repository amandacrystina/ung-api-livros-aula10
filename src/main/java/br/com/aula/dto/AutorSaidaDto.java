package br.com.aula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutorSaidaDto(
    @NotNull Long id,
    @NotBlank String nome
) {}