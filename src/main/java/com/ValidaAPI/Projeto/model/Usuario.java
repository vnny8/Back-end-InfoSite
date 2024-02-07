package com.ValidaAPI.Projeto.model;

import com.ValidaAPI.Projeto.dto.CadastroUsuarioDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = true)
    private String nome;

    @Column(name="login", unique=true, nullable = true)
    private String login;

    @Column(name = "senha", nullable = true)
    private String senha;

    @Column(name="imagem")
    private byte[] imagem;

    public Usuario(String nome, String login, String senha,byte[] imagem) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.imagem = imagem;
    }
    public Usuario(){

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
