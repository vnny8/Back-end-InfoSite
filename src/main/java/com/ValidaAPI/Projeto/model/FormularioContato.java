package com.ValidaAPI.Projeto.model;

import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name="formulario_contato")
public class FormularioContato {

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getAssunto() {
        return assunto;
    }

    public Instant getDataEnvio() {
        return dataEnvio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    public String getTelefone() {
        return telefone;
    }

    private String telefone;
    @Column(columnDefinition = "TEXT")
    private String assunto;
    private Instant dataEnvio;

    public FormularioContato(){

    };

    public FormularioContato(CadastroFormularioContatoDto dto) {
        this.nome = dto.nome();
        this.email = dto.email().toLowerCase();
        this.telefone = dto.telefone();
        this.assunto = dto.assunto();
        this.dataEnvio = Instant.now();
    }

    @Override
    public String toString(){
        return "id: "+this.id+" nome: "+this.nome+" email: "+this.email+" assunto: "+this.assunto+" dataEnvio: "+this.dataEnvio;
    }
}
