package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import com.ValidaAPI.Projeto.dto.FormularioContatoDto;
import com.ValidaAPI.Projeto.model.Enviado;
import com.ValidaAPI.Projeto.model.FormularioContato;
import com.ValidaAPI.Projeto.repository.FormularioContatoRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FormularioContatoServiceTest {
    @Mock
    private FormularioContatoRepository formularioContatoRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private FormularioContatoService formularioContatoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar a lista de todos os formulários")
    void listarCase1() {
        // Cria a lista de formulários que o repositório deve retornar
        List<FormularioContato> listaDeFormularios = new ArrayList<>();
        listaDeFormularios.add(new FormularioContato(new CadastroFormularioContatoDto("Teste", "testando@hotmail.com", "99999-9999", "Teste", Enviado.EXITO)));
        listaDeFormularios.add(new FormularioContato(new CadastroFormularioContatoDto("Teste", "testando@hotmail.com", "99999-9999", "Teste2", Enviado.EXITO)));

        // Simula o comportamento do findAll do repositório
        when(formularioContatoRepo.findAll()).thenReturn(listaDeFormularios);

        // Executa o método de listar do serviço
        List<FormularioContatoDto> resultado = formularioContatoService.listar();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
    }

    @Test
    @DisplayName("Não retorna lista de todos os formulários quando não existe")
    void listarCase2() {
        // Cria a lista de formulários que o repositório deve retornar
        List<FormularioContato> listaDeFormularios = new ArrayList<>();

        // Simula o comportamento do findAll do repositório
        when(formularioContatoRepo.findAll()).thenReturn(listaDeFormularios);

        // Executa o método de listar do serviço
        List<FormularioContatoDto> resultado = formularioContatoService.listar();

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar as listas de formulários de um email")
    void listarPorEmailCase1() {
        // Cria a lista de formulários que o repositório deve retornar
        String email = "Testando@hotmail.com";
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", email, "99999-9999", "Teste", Enviado.EXITO);
        CadastroFormularioContatoDto data2 = new CadastroFormularioContatoDto("Teste", email, "99999-9999", "Teste2", Enviado.EXITO);
        List<FormularioContato> listaDeFormularios = new ArrayList<>();
        listaDeFormularios.add(new FormularioContato(data));
        listaDeFormularios.add(new FormularioContato(data2));

        // Simula o comportamento do findAll do repositório
        when(formularioContatoRepo.findAllByEmail(email)).thenReturn(listaDeFormularios);

        // Executa o método de listar do serviço
        List<FormularioContatoDto> resultado = formularioContatoService.listarPorEmail(email);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(dto -> dto.email().equalsIgnoreCase(email));
    }

    @Test
    @DisplayName("Não retornar a lista de formulários de um email quando não existe")
    void listarPorEmailCase2() {
        // Cria a lista de formulários que o repositório deve retornar
        String email = "Testando@hotmail.com";
        List<FormularioContato> listaDeFormularios = new ArrayList<>();
        // Simula o comportamento do findAll do repositório
        when(formularioContatoRepo.findAllByEmail(email)).thenReturn(listaDeFormularios);

        // Executa o método de listar do serviço
        List<FormularioContatoDto> resultado = formularioContatoService.listarPorEmail(email);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve cadastrar um formulário com êxito")
    void cadastrarCase1() throws MessagingException, IOException {
        // Configurar o DTO com dados válidos
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "testando@hotmail.com", "99999-9999", "Assunto", Enviado.EXITO);
        // Simular o comportamento de sucesso do emailService
        doNothing().when(emailService).enviarEmails(data);

        // Simular o repositório salvando o formulário
        when(formularioContatoRepo.save(any(FormularioContato.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Executar o método cadastrar
        boolean resultado = formularioContatoService.cadastrar(data);

        // Verificar se o resultado é verdadeiro
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Não cadastra um usuário com êxito")
    void cadastrarCase2() throws MessagingException, IOException {
        // Configura o DTO
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "testando@hotmail.com", "99999-9999", "Assunto", Enviado.FALHA);

        // Configura o mock para lançar uma exceção
        doThrow(MessagingException.class).when(emailService).enviarEmails(any(CadastroFormularioContatoDto.class));

        // Executa o método cadastrar
        boolean resultado = formularioContatoService.cadastrar(data);

        // Verifica se o resultado é falso
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Não cadastra um usuário com êxito")
    void cadastrarCase3() throws MessagingException, IOException {
        // Configura o DTO
        CadastroFormularioContatoDto data = new CadastroFormularioContatoDto("Teste", "testando@hotmail.com", "99999-9999", "Assunto", Enviado.FALHA);

        // Configura o mock para lançar uma exceção
        doThrow(IOException.class).when(emailService).enviarEmails(any(CadastroFormularioContatoDto.class));

        // Executa o método cadastrar
        boolean resultado = formularioContatoService.cadastrar(data);

        // Verifica se o resultado é falso
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve deletar um usuário por ID")
    void deletarPorIdCase1() {
        Long id = 2L;
        when(formularioContatoRepo.existsById(id)).thenReturn(true);


        // Ação e Verificação
        assertDoesNotThrow(() -> formularioContatoService.deletarPorId(id));

        // Verifica se deleteById nunca foi chamado
        verify(formularioContatoRepo).deleteById(id);
    }

    @Test
    @DisplayName("Não deleta um usuário quando ele não existe")
    void deletarPorIdCase2() {
        Long id = 2L;
        when(formularioContatoRepo.existsById(id)).thenReturn(false);


        // Ação e Verificação
        assertThrows(RuntimeException.class, () -> formularioContatoService.deletarPorId(id));

        // Verifica se deleteById nunca foi chamado
        verify(formularioContatoRepo, never()).deleteById(id);
    }

    @Test
    @DisplayName("Deve enviar os email que deram falha")
    void enviarEmailsPendentesAgendadoCase1() throws MessagingException, IOException {
        // Preparar dados de teste
        List<FormularioContato> formulariosPendentes = List.of(
                new FormularioContato(new CadastroFormularioContatoDto("Nome1", "email1@test.com", "11111", "Assunto1", Enviado.FALHA)),
                new FormularioContato(new CadastroFormularioContatoDto("Nome2", "email2@test.com", "222622", "Assunto2", Enviado.FALHA))
        );

        // Simular comportamento do repositório
        when(formularioContatoRepo.findPendentes()).thenReturn(formulariosPendentes);

        // Configurar o serviço de e-mail para não lançar exceções
        doNothing().when(emailService).enviarEmails(any(CadastroFormularioContatoDto.class));

        // Executar o método sob teste
        formularioContatoService.enviarEmailsPendentesAgendado();

        // Verificar se o serviço de e-mail foi chamado para cada formulário pendente
        verify(emailService, times(formulariosPendentes.size())).enviarEmails(any(CadastroFormularioContatoDto.class));
    }

    @Test
    @DisplayName("Não enviar os email que deram falha quando não existem")
    void enviarEmailsPendentesAgendadoCase2() throws MessagingException, IOException {
        // Preparar dados de teste
        List<FormularioContato> formulariosPendentes = new ArrayList<>();
        // Simular comportamento do repositório
        when(formularioContatoRepo.findPendentes()).thenReturn(formulariosPendentes);

        // Configurar o serviço de e-mail para não lançar exceções
        doNothing().when(emailService).enviarEmails(any(CadastroFormularioContatoDto.class));

        // Executar o método sob teste
        formularioContatoService.enviarEmailsPendentesAgendado();

        // Verificar se o serviço de e-mail foi chamado para cada formulário pendente
        verify(emailService, never()).enviarEmails(any(CadastroFormularioContatoDto.class));
    }
}