package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.model.Usuario;
import com.ValidaAPI.Projeto.repository.IUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private IUsuario repository;

    public UsuarioService(IUsuario repository){
        this.repository = repository;
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> lista = repository.findAll();
        return lista;
    }

    public Usuario criarUsuario(Usuario cadastro){
        Usuario novoUsuario = repository.save(cadastro);
        return novoUsuario;
    }

    public Usuario editarUsuario(Usuario usuario){
        Usuario usuarionovo = repository.save(usuario);
        return usuarionovo;
    }

    public Boolean excluirUsuario(Integer id){
        repository.deleteById(id);
        return true;
    }
}
