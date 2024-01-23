package com.ValidaAPI.Projeto.dto;

import jakarta.validation.constraints.NotNull;

public record CadastroFormularioContatoDto(
        @NotNull
        String nome,
        @NotNull
        String email,
        @NotNull
        String telefone,
        @NotNull
        String assunto) {
}
