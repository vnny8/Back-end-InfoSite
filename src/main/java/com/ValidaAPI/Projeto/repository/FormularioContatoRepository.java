package com.ValidaAPI.Projeto.repository;

import com.ValidaAPI.Projeto.model.FormularioContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FormularioContatoRepository extends JpaRepository<FormularioContato,Long> {

    @Query("SELECT f FROM FormularioContato f WHERE LOWER(f.email) = LOWER(:email)")
    List<FormularioContato> findAllByEmail(String email);

    @Query("SELECT f FROM FormularioContato f WHERE f.enviado = FALHA")
    List<FormularioContato> findPendentes();
}
