package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.dto.CadastroUsuarioDto;
import com.ValidaAPI.Projeto.dto.UsuarioDto;
import com.ValidaAPI.Projeto.repository.UsuarioRepository;
import com.ValidaAPI.Projeto.model.Usuario;
import com.ValidaAPI.Projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
// Endereço de acesso geral, ai não precisa colocar no GetMapping, exceto se for mais específico

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        return ResponseEntity.status(200).body(usuarioService.listarUsuarios());
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody @Valid CadastroUsuarioDto cadastro){
        return ResponseEntity.status(201).body(usuarioService.criarUsuario(cadastro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> editarUsuario(@RequestBody UsuarioDto cadastroEditado,@PathVariable Long id){
        return ResponseEntity.status(200).body(usuarioService.editarUsuario(cadastroEditado,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> encontrarUsuario(@PathVariable Long id){
        Optional<Usuario> encontrarUsuario = usuarioRepository.findById(id);
        return encontrarUsuario;
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
