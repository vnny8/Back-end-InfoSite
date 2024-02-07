package com.ValidaAPI.Projeto.infra.security;

public record TokenDto(String tokenJWT, String nome, byte[] imagem) {

}
