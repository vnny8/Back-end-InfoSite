package com.ValidaAPI.Projeto.repository;

import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import com.ValidaAPI.Projeto.dto.FormularioContatoDto;
import com.ValidaAPI.Projeto.model.Enviado;
import com.ValidaAPI.Projeto.model.FormularioContato;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.Normalizer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class FormularioContatoRepositoryTest {

    @Autowired
    FormularioContatoRepository FCR;

    @Autowired
    EntityManager entityManager;
    @Test
    @DisplayName("Deve retornar os todos formulários cadastrados por email")
    void findAllByEmailCase1() {
        Enviado enviado = Enviado.EXITO;
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste", enviado);
        CadastroFormularioContatoDto data2 = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste2", enviado);
        this.createFormulario(data);
        this.createFormulario(data2);
        List<FormularioContato> formularios = this.FCR.findAllByEmail(data.email().toLowerCase());
        assertThat(formularios).isNotEmpty();
    }

    @Test
    @DisplayName("Não deve retornar os todos formulários cadastrados por email quando não existe")
    void findAllByEmailCase2() {
        String email = "Nãoexiste@hotmail.com";
        List<FormularioContato> formularios = this.FCR.findAllByEmail(email);
        assertThat(formularios).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar os todos formulários que não foram enviados")
    void findPendentesCase1() {
        Enviado enviado = Enviado.EXITO;
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste", enviado);
        CadastroFormularioContatoDto data2 = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste2", Enviado.FALHA);
        CadastroFormularioContatoDto data3 = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste3", Enviado.FALHA);
        this.createFormulario(data);
        this.createFormulario(data2);
        this.createFormulario(data3);
        List<FormularioContato> formularios = this.FCR.findPendentes();
        assertThat(formularios).isNotEmpty();
    }

    @Test
    @DisplayName("Não retorna os formulários não enviados pois não existe")
    void findPendentesCase2() {
        Enviado enviado = Enviado.EXITO;
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste", enviado);
        CadastroFormularioContatoDto data2 = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste2", Enviado.FALHA);
        CadastroFormularioContatoDto data3 = new CadastroFormularioContatoDto("Teste", "Testando@hotmail.com", "99999-9999", "Teste3", Enviado.FALHA);
        this.createFormulario(data);
        this.createFormulario(data2);
        this.createFormulario(data3);
        List<FormularioContato> formularios = this.FCR.findPendentes();
        assertThat(formularios).isNotEmpty();
    }

    private FormularioContato createFormulario(CadastroFormularioContatoDto data){
        FormularioContato FC = new FormularioContato(data);
        this.entityManager.persist(FC);
        return FC;
    }
}