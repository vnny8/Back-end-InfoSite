package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(@NotNull Long id, @NotBlank String nome, @NotBlank String login, @NotBlank String senha, @NotNull byte[] imagem) {
    public UsuarioDto(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getSenha(),usuario.getImagem());
    }
}
