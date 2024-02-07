package com.ValidaAPI.Projeto.dto;

import com.ValidaAPI.Projeto.model.Projeto;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CadastroProjetoDto(@NotNull String titulo,
                                 @NotNull String descricao,
                                 @NotNull byte[] imagem) {}
