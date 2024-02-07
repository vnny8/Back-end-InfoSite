package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Enviado;
import com.ValidaAPI.Projeto.model.FormularioContato;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;


public record FormularioContatoDto(@NotNull Long id,@NotNull String nome,@NotNull String email,@NotNull String telefone,@NotNull String assunto,@NotNull Instant dataEnvio,@NotNull Enviado enviado) {
    public FormularioContatoDto(FormularioContato formularioContato){
        this(formularioContato.getId(), formularioContato.getNome(), formularioContato.getEmail(), formularioContato.getTelefone(), formularioContato.getAssunto(), formularioContato.getDataEnvio(),formularioContato.getEnviado());
    }
}