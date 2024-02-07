package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.dto.CadastroUsuarioDto;
import com.ValidaAPI.Projeto.dto.UsuarioDto;
import com.ValidaAPI.Projeto.repository.UsuarioRepository;
import com.ValidaAPI.Projeto.model.Usuario;
import com.ValidaAPI.Projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/usuarios")
// Endereço de acesso geral, ai não precisa colocar no GetMapping, exceto se for mais específico

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){

        return ResponseEntity.status(200).body(usuarioService.listarUsuarios());
    }

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody @Valid CadastroUsuarioDto cadastro, UriComponentsBuilder uriBuilder){
        try {
            UsuarioDto cadastroNovo = usuarioService.criarUsuario(cadastro);
            URI uri = uriBuilder.path("/api/usuarios/{id}").buildAndExpand(cadastroNovo.id()).toUri();
            return ResponseEntity.created(uri).body(cadastroNovo);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível cadastrar o usuário!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity editarUsuario(@RequestBody UsuarioDto cadastroEditado,@PathVariable Long id){
        try {
            return ResponseEntity.status(200).body(usuarioService.editarUsuario(cadastroEditado, id));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> encontrarUsuario(@PathVariable Long id){
        return usuarioRepository.findById(id);
    }

//    @GetMapping("/login/{login}/{senha}")
//    public Integer encontrarUsuarioNome(@PathVariable String login, @PathVariable String senha){
//        List<Usuario> usuarios = usuariosCadastrados();
//        for (Usuario usuario : usuarios){
//            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
//                return 1;
//            }
//        }
//        return 0;
//    }
}
