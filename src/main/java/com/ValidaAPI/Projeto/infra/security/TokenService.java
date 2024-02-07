package com.ValidaAPI.Projeto.infra.security;

import com.ValidaAPI.Projeto.dto.UsuarioDto;
import com.ValidaAPI.Projeto.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Service
public class TokenService {
    @Value("${infocorp.api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Infocorp.site")
                    .withSubject(usuario.getLogin())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("imagem", Collections.singletonList(usuario.getImagem()))
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar token JWT: ",e);
        }
    }
    public String getSubject(String tokenJWT){
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Infocorp.site")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();


    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-04:00"));
    }
}
