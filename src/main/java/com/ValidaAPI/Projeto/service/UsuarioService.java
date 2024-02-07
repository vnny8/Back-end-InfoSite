package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.dto.CadastroUsuarioDto;
import com.ValidaAPI.Projeto.dto.UsuarioDto;
import com.ValidaAPI.Projeto.model.Usuario;
import com.ValidaAPI.Projeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarUsuarios(){
        List<Usuario> lista = repository.findAll();
        return lista;
    }

    public UsuarioDto criarUsuario(CadastroUsuarioDto usuario){
        String senhaCodificada = passwordEncoder.encode(usuario.senha());
        Usuario novoUsuario = repository.save(new Usuario(usuario.nome(), usuario.login(), senhaCodificada,usuario.imagem()));
        return new UsuarioDto(novoUsuario);

    }

    public UsuarioDto editarUsuario(UsuarioDto usuario,Long id){
        Usuario usuarioExistente = repository.findById(id).orElseThrow(()->new RuntimeException("Usuário não encontrado!"));
        if(repository.existsByLogin(usuario.nome())){
            throw new RuntimeException("Já existe um usuário com este login");
        }
        String senhaCodificada = passwordEncoder.encode(usuario.senha());
        usuarioExistente.setNome(usuario.nome());
        usuarioExistente.setLogin(usuario.login());
        usuarioExistente.setSenha(senhaCodificada);
        usuarioExistente.setImagem(usuario.imagem());
        return new UsuarioDto(repository.save(usuarioExistente));
    }

    public void excluirUsuario(Long id){
        repository.deleteById(id);
    }
}
