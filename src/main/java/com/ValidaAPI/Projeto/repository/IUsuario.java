package com.ValidaAPI.Projeto.repository;

import com.ValidaAPI.Projeto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuario extends JpaRepository<Usuario, Integer> {

}
