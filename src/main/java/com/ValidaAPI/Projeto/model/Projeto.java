package com.ValidaAPI.Projeto.model;

import com.ValidaAPI.Projeto.dto.CadastroProjetoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@Entity
@Table(name="projetos")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private String link;
    private boolean ativo;
    private byte[] imagem;

    public Projeto(CadastroProjetoDto dto) throws IOException {
        this.titulo = dto.titulo();
        this.descricao = dto.descricao();
        this.imagem = dto.imagem();
        this.link = dto.link();
        this.ativo = dto.ativo();
    }

    public Projeto() {

    }
}
