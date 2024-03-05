package com.ValidaAPI.Projeto.controller;

import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import com.ValidaAPI.Projeto.dto.FormularioContatoDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.ValidaAPI.Projeto.service.FormularioContatoService;

import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin("*")
@RequestMapping("api/contato")
public class FormularioContatoController {

    @Autowired
    FormularioContatoService formularioContatoService;

    @GetMapping
    public ResponseEntity<List<FormularioContatoDto>> listar(){
        List<FormularioContatoDto> formulariosContato = formularioContatoService.listar();
        return ResponseEntity.ok(formulariosContato);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<FormularioContatoDto>> listarPorEmail(@PathVariable String email){
        List<FormularioContatoDto> formularioEncontrado = formularioContatoService.listarPorEmail(email.toLowerCase());
        return ResponseEntity.ok(formularioEncontrado);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroFormularioContatoDto dto){
        try {
            if(formularioContatoService.cadastrar(dto)){
                return ResponseEntity.ok("Requisição de envio feita com sucesso!");
            }else{
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Formulário salvo no banco mas não foi possível enviar o e-mail");
            }
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        try{
            formularioContatoService.deletarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
        }catch(RuntimeException exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> atualizar(@RequestBody @Valid FormularioContato Dto,@PathVariable Long id){
//
//    }

}

