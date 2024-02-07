package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Projeto;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record ProjetoDto(
        @NotNull Long id,
        @NotNull String titulo,
        @NotNull String descricao,
        @NotNull byte[] imagem
) {
    public ProjetoDto(Projeto projeto){
        this(projeto.getId(),projeto.getTitulo(),projeto.getDescricao(),projeto.getImagem());
    }

    public ProjetoDto(Optional<Projeto> projeto) {
        this(projeto.get().getId(),projeto.get().getTitulo(),projeto.get().getDescricao(),projeto.get().getImagem());

    }
}