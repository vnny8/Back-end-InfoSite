package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.dto.CadastroProjetoDto;
import com.ValidaAPI.Projeto.dto.ProjetoDto;
import com.ValidaAPI.Projeto.model.FormularioContato;
import com.ValidaAPI.Projeto.repository.ProjetoRepository;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ValidaAPI.Projeto.model.Projeto;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar um projeto com sucesso")
    void cadastrarCase1() throws IOException {
        byte[] imagem = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
        CadastroProjetoDto CProjeto = new CadastroProjetoDto("Teste", "Testando", imagem, "testando.com", true);
        Projeto p = new Projeto(CProjeto);

        when(projetoRepository.save(any(Projeto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ação
        ProjetoDto resultado = projetoService.cadastrar(CProjeto);


        assertThat(resultado).isNotNull();
        assertThat(resultado.titulo()).isEqualTo(CProjeto.titulo());
        assertThat(resultado.descricao()).isEqualTo(CProjeto.descricao());
        assertThat(resultado.imagem()).isEqualTo(CProjeto.imagem());
        assertThat(resultado.link()).isEqualTo(CProjeto.link());
        assertThat(resultado.ativo()).isEqualTo(CProjeto.ativo());

        // Verifica se o método save foi chamado
        verify(projetoRepository).save(any(Projeto.class));
    }

    @Test
    @DisplayName("Não consegue cadastrar um projeto com sucesso")
    void cadastrarCase2() throws IOException, RuntimeException {
        byte[] imagem = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
        CadastroProjetoDto CProjeto = new CadastroProjetoDto("Teste", "Testando", imagem, "testando.com", true);
        Projeto p = new Projeto(CProjeto);

        // Configura o mock para lançar IOException
        doThrow(new RuntimeException("Erro de acesso ao banco")).when(projetoRepository).save(any(Projeto.class));

        // Executa e verifica
        assertThrows(RuntimeException.class, () -> projetoService.cadastrar(CProjeto));

        // Verifica se o método save foi chamado
        verify(projetoRepository).save(any(Projeto.class));
    }

    @Test
    @DisplayName("Deve listar todos os projetos")
    void listarProjetosCase1() throws IOException {
        byte[] imagem = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
        CadastroProjetoDto CProjeto = new CadastroProjetoDto("Teste", "Testando", imagem, "testando.com", true);
        Projeto p = new Projeto(CProjeto);
        List<Projeto> projetos = new ArrayList<>();
        projetos.add(p);

        when(projetoRepository.findAll()).thenReturn(projetos);

        List<ProjetoDto> resultado = projetoService.listarProjetos();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).id()).isEqualTo(p.getId());
        verify(projetoRepository).findAll();
    }

    @Test
    @DisplayName("Não listar todos os projetos quando tá vazio")
    void listarProjetosCase2() throws IOException {
        List<Projeto> projetos = new ArrayList<>();

        when(projetoRepository.findAll()).thenReturn(projetos);

        List<ProjetoDto> resultado = projetoService.listarProjetos();

        assertThat(resultado).isEmpty();
        verify(projetoRepository).findAll();
    }

    @Test
    @DisplayName("Retorna o projeto com id correspondente")
    void listarPorIdCase1() throws IOException {
        byte[] imagem = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
        CadastroProjetoDto CProjeto = new CadastroProjetoDto("Teste", "Testando", imagem, "testando.com", true);
        Projeto p = new Projeto(CProjeto);

        when(projetoRepository.findById(p.getId())).thenReturn(Optional.of(p));

        Optional<Projeto> resultado = projetoService.listarPorId(p.getId());

        assertThat(resultado).isNotEmpty();
        verify(projetoRepository).findById(p.getId());
    }

    @Test
    @DisplayName("Não retorna um projeto quando o id não existe")
    void listarPorIdCase2() {
        Long id = 1L;
        when(projetoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Projeto> resultado = projetoService.listarPorId(id);

        assertThat(resultado).isEmpty();
        verify(projetoRepository).findById(id);
    }

    @Test
    @DisplayName("Deve editar um projeto por ID")
    void editarProjetoPorIdCase1() throws IOException {
        Long id = 1L;
        byte[] imagem = new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        ProjetoDto projetoEditadoDto = new ProjetoDto(id, "Teste editado", "Testando edit", imagem, "teste.com", true);
        Projeto projetoExistente = new Projeto(new CadastroProjetoDto("Teste", "Testando", imagem, "testando.com", true));

        when(projetoRepository.findById(id)).thenReturn(Optional.of(projetoExistente));
        when(projetoRepository.save(any(Projeto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjetoDto resultado = projetoService.editarProjetoPorId(projetoEditadoDto, id);

        assertThat(resultado).isNotNull();
        assertThat(resultado.titulo()).isEqualTo(projetoEditadoDto.titulo());
        assertThat(resultado.descricao()).isEqualTo(projetoEditadoDto.descricao());
        assertThat(resultado.link()).isEqualTo(projetoEditadoDto.link());
        assertThat(resultado.ativo()).isEqualTo(projetoEditadoDto.ativo());
        assertThat(resultado.imagem()).isEqualTo(projetoEditadoDto.imagem());

        verify(projetoRepository, times(2)).findById(id);
        verify(projetoRepository).save(any(Projeto.class));
    }

    @Test
    @DisplayName("Deve editar um projeto por ID")
    void editarProjetoPorIdCase2() {
        Long id = 1L;
        byte[] imagem = new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        ProjetoDto projetoEditadoDto = new ProjetoDto(id, "Teste Editado", "Testando Editado", imagem, "editado.com", true);

        when(projetoRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> projetoService.editarProjetoPorId(projetoEditadoDto, id));

        assertThat(exception.getMessage()).contains("Não foi encontrado nenhum projeto com o id informado!");
        verify(projetoRepository).findById(id);
    }

    @Test
    @DisplayName("Deve deletar um projeto por ID")
    void deletarProjetoPorIdCase1() {
        Long id = 1L;

        doNothing().when(projetoRepository).deleteById(id);

        projetoService.deletarProjetoPorId(id);

        verify(projetoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Não deleta um projeto por ID quando não existe")
    void deletarProjetoPorIdCase2() {
        Long id = 2L;

        projetoService.deletarProjetoPorId(id);

        verify(projetoRepository).deleteById(id);
    }
}