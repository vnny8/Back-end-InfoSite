package com.ValidaAPI.Projeto.DAO;

import com.ValidaAPI.Projeto.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuario extends CrudRepository<Usuario, Integer> {

}
