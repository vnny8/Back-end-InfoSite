package com.ValidaAPI.Projeto.repository;

import com.ValidaAPI.Projeto.model.FormularioContato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FormularioContatoRepository extends JpaRepository<FormularioContato,Long> {

    List<FormularioContato> findAllByEmail(String email);
}
