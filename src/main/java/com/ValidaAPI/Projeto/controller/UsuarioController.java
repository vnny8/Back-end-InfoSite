package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.DAO.IUsuario;
import com.ValidaAPI.Projeto.model.Usuario;
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

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        List<Usuario> lista = (List<Usuario>) DAO.findAll();
        return ResponseEntity.status(200).body(lista);
    }

    public List<Usuario> usuariosCadastrados(){
        return (List<Usuario>) DAO.findAll();
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario cadastro){
        Usuario novoUsuario = DAO.save(cadastro);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario cadastroEditado){
        Usuario novoUsuario = DAO.save(cadastroEditado);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Integer id){
        DAO.deleteById(id);
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
