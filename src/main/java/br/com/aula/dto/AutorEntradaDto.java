package br.com.aula.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorEntradaDto(
    @NotBlank String nome
) {}