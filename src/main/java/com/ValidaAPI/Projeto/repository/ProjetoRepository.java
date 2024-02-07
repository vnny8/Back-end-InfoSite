package com.ValidaAPI.Projeto.repository;

import com.ValidaAPI.Projeto.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto,Long> {
}
