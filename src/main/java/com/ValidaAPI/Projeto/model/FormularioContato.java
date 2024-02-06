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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    @Column(columnDefinition = "TEXT")
    private String assunto;
    private Instant dataEnvio;
    @Enumerated(EnumType.STRING)
    private Enviado enviado;
    private Integer tentativasEnvio;

    public FormularioContato(){

    };

    public FormularioContato(CadastroFormularioContatoDto dto) {
        this.nome = dto.nome();
        this.email = dto.email().toLowerCase();
        this.telefone = dto.telefone();
        this.assunto = dto.assunto();
        this.dataEnvio = Instant.now();
        this.enviado = dto.enviado();
        this.tentativasEnvio = 1;
    }

    public FormularioContato(Long id, String nome, String email, String telefone, String assunto, Enviado enviado,Instant dataEnvio,Integer tentativasEnvio) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.assunto = assunto;
        this.enviado = enviado;
        this.dataEnvio = dataEnvio;
        this.tentativasEnvio = tentativasEnvio;
    }

    @Override
    public String toString(){
        return "id: "+" nome: "+this.nome+" email: "+this.email+" assunto: "+this.assunto+" dataEnvio: "+this.dataEnvio+" enviado: "+this.enviado+" tentativasEnvio: "+this.tentativasEnvio;
    }
}
