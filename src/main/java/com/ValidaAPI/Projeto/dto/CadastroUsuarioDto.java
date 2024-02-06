package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioDto(@NotNull String nome, @NotNull String login, @NotNull String senha) {

}
