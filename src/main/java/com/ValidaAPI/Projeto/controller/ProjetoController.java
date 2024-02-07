package com.ValidaAPI.Projeto.controller;


import com.ValidaAPI.Projeto.dto.CadastroProjetoDto;
import com.ValidaAPI.Projeto.dto.ProjetoDto;
import com.ValidaAPI.Projeto.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public ResponseEntity listarProjetos(){
        try{
            List<ProjetoDto> listaDeProjetos = projetoService.listarProjetos();
            return ResponseEntity.status(HttpStatus.OK).body(listaDeProjetos);
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível recuperar a lista de projetos!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity listarProjetoPorId(@PathVariable Long id){
        try{
            if(projetoService.listarPorId(id).isPresent()) {
                ProjetoDto projetoEncontrado = new ProjetoDto(projetoService.listarPorId(id));
                return ResponseEntity.status(HttpStatus.OK).body(projetoEncontrado);
            }else{
                throw new RuntimeException("Não foi encontrado nenhum projeto com o id informado");
            }
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity cadastrarProjeto(@RequestBody CadastroProjetoDto dto, UriComponentsBuilder uriBuilder) throws IOException{
        try{
            ProjetoDto projeto = projetoService.cadastrar(dto);
            URI uri = uriBuilder.path("/api/projetos/{id}").buildAndExpand(projeto.id()).toUri();
            return ResponseEntity.created(uri).body(projeto);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity editarProjeto(@RequestBody ProjetoDto projetoEditado, @PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(projetoService.editarProjetoPorId(projetoEditado,id));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarProjeto(@PathVariable Long id){
        try{
            projetoService.deletarProjetoPorId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
