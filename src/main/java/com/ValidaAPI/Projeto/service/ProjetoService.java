package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.dto.CadastroProjetoDto;
import com.ValidaAPI.Projeto.dto.ProjetoDto;
import com.ValidaAPI.Projeto.model.Projeto;
import com.ValidaAPI.Projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public ProjetoDto cadastrar(CadastroProjetoDto dto) throws IOException {
        Projeto projetoNovo = new Projeto(dto);
        projetoRepository.save(projetoNovo);
        return new ProjetoDto(projetoNovo);
    }

    public List<ProjetoDto> listarProjetos(){
        return projetoRepository.findAll().stream().map(ProjetoDto::new).toList();
    }

    public Optional<Projeto> listarPorId(Long id){
        return projetoRepository.findById(id);
    }

    public ProjetoDto editarProjetoPorId(ProjetoDto projetoEditado,Long id){
        if(listarPorId(id).isPresent()){
            Projeto projeto = projetoRepository.findById(id).get();
            projeto.setTitulo(projetoEditado.titulo());
            projeto.setDescricao(projetoEditado.descricao());
            projeto.setAtivo(projetoEditado.ativo());
            projeto.setLink(projetoEditado.link());
            projeto.setImagem(projetoEditado.imagem());
            projetoRepository.save(projeto);
            return new ProjetoDto(projeto);
        }
        else throw new RuntimeException("Não foi encontrado nenhum projeto com o id informado!");
    }

    public void deletarProjetoPorId(Long id){
        projetoRepository.deleteById(id);
    }
}
