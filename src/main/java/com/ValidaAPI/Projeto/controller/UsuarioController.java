package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.DAO.IUsuario;
import com.ValidaAPI.Projeto.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Usuario> listaUsuarios(){
        return (List<Usuario>) DAO.findAll();
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario cadastro){
        Usuario novoUsuario = DAO.save(cadastro);
        return novoUsuario;
    }

    @PutMapping
    public Usuario editarUsuario(@RequestBody Usuario cadastroEditado){
        Usuario novoUsuario = DAO.save(cadastroEditado);
        return novoUsuario;
    }

    @DeleteMapping("/{id}")
    public Optional<Usuario> excluirUsuario(@PathVariable Integer id){
        Optional<Usuario> usuarioApagado = DAO.findById(id);
        DAO.deleteById(id);
        return usuarioApagado;
    }

    @GetMapping("/{id}")
    public Optional<Usuario> encontrarUsuario(@PathVariable Integer id){
        Optional<Usuario> encontrarUsuario = DAO.findById(id);
        return encontrarUsuario;
    }

    @GetMapping("/login/{login}/{senha}")
    public Integer encontrarUsuarioNome(@PathVariable String login, @PathVariable String senha){
        List<Usuario> usuarios = listaUsuarios();
        for (Usuario usuario : usuarios){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return 1;
            }
        }
        return 0;
    }
}
