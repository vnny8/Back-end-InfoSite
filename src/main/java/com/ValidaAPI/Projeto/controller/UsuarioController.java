package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.repository.IUsuario;
import com.ValidaAPI.Projeto.model.Usuario;
import com.ValidaAPI.Projeto.service.UsuarioService;
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
    private IUsuario DAO;

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        return ResponseEntity.status(200).body(usuarioService.listarUsuarios());
    }

    public List<Usuario> usuariosCadastrados(){
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario cadastro){
        return ResponseEntity.status(201).body(usuarioService.criarUsuario(cadastro));
    }

    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario cadastroEditado){
        return ResponseEntity.status(200).body(usuarioService.editarUsuario(cadastroEditado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Integer id){
        usuarioService.excluirUsuario(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> encontrarUsuario(@PathVariable Integer id){
        Optional<Usuario> encontrarUsuario = DAO.findById(id);
        return encontrarUsuario;
    }

    @GetMapping("/login/{login}/{senha}")
    public Integer encontrarUsuarioNome(@PathVariable String login, @PathVariable String senha){
        List<Usuario> usuarios = usuariosCadastrados();
        for (Usuario usuario : usuarios){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return 1;
            }
        }
        return 0;
    }
}
