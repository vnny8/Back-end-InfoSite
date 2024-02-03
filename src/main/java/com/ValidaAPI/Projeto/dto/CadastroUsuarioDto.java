package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Usuario;
import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioDto(@NotBlank String nome,@NotBlank String login,@NotBlank String senha) {

}
