package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.FormularioContato;

import java.time.Instant;


public record FormularioContatoDto(Long id, String nome, String email, String telefone, String assunto, Instant dataEnvio) {
    public FormularioContatoDto(FormularioContato formularioContato){
        this(formularioContato.getId(), formularioContato.getNome(), formularioContato.getEmail(), formularioContato.getTelefone(), formularioContato.getAssunto(), formularioContato.getDataEnvio());
    }
}
