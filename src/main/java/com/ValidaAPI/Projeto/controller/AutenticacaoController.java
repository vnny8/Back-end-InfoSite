package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.dto.UsuarioAutenticacaoDto;
import com.ValidaAPI.Projeto.dto.UsuarioDto;
import com.ValidaAPI.Projeto.infra.security.TokenDto;
import com.ValidaAPI.Projeto.infra.security.TokenService;
import com.ValidaAPI.Projeto.model.Usuario;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController{
    @Autowired
    public AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioAutenticacaoDto dto){

            var AuthenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
            var authentication =  manager.authenticate(AuthenticationToken);

            var tokenJWT = tokenService.gerarToken((Usuario)authentication.getPrincipal());
            Usuario usuario = (Usuario)authentication.getPrincipal();
            return ResponseEntity.ok(new TokenDto(tokenJWT,usuario.getNome(),usuario.getImagem()));

    }

}
