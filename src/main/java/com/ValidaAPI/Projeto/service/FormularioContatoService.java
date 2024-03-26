package com.ValidaAPI.Projeto.service;
import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import com.ValidaAPI.Projeto.dto.FormularioContatoDto;
import com.ValidaAPI.Projeto.model.Enviado;
import com.ValidaAPI.Projeto.model.FormularioContato;
import com.ValidaAPI.Projeto.repository.FormularioContatoRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class FormularioContatoService {

    @Autowired
    private FormularioContatoRepository formularioContatoRepo;

    @Autowired
    private EmailService emailService;

    public List<FormularioContatoDto> listar(){
        List<FormularioContato> formulariosContato = formularioContatoRepo.findAll();
        return formulariosContato
                .stream()
                .map(FormularioContatoDto::new)
                .toList();
    }

    public List<FormularioContatoDto> listarPorEmail(String email){
        List<FormularioContato> formulariosEncontrados = formularioContatoRepo.findAllByEmail(email);
        return formulariosEncontrados
                .stream()
                .map(FormularioContatoDto::new)
                .toList();
    }




    public boolean cadastrar(CadastroFormularioContatoDto dto){
        try {
            emailService.enviarEmails(dto);
            CadastroFormularioContatoDto formularioNovo = new CadastroFormularioContatoDto(dto.nome(),dto.email(),dto.telefone(),dto.assunto(),Enviado.EXITO);
            formularioContatoRepo.save(new FormularioContato(formularioNovo));
        }catch (MailException | IOException | MessagingException e) {
            CadastroFormularioContatoDto formularioNovo = new CadastroFormularioContatoDto(dto.nome(),dto.email(),dto.telefone(),dto.assunto(),Enviado.FALHA);
            formularioContatoRepo.save(new FormularioContato(formularioNovo));
            return false;
        }

        return true;

    }

    public void deletarPorId(Long id){
        if(!formularioContatoRepo.existsById(id)){
            throw new RuntimeException("Formulário não encontrado com o id informado");
        }
        formularioContatoRepo.deleteById(id);
    }

    @Scheduled(fixedDelay = 60000) // 600000 milissegundos = 1 minuto
    public void enviarEmailsPendentesAgendado() throws MessagingException, IOException {
        List<FormularioContato> emailsPendentes = formularioContatoRepo.findPendentes();

        if (!emailsPendentes.isEmpty()) {
            for (FormularioContato email : emailsPendentes) {
                try {
                    emailService.enviarEmails(new CadastroFormularioContatoDto(email.getNome(), email.getEmail(), email.getTelefone(), email.getAssunto(), email.getEnviado()));
                    FormularioContato formulario = new FormularioContato(email.getId(), email.getNome(), email.getEmail(), email.getTelefone(), email.getAssunto(), Enviado.EXITO, email.getDataEnvio(), email.getTentativasEnvio());
                    formularioContatoRepo.save(formulario);
                } catch (Exception e) {
                    if (email.getTentativasEnvio() >= 3) {
                        formularioContatoRepo.delete(email);
                    } else {
                        email.setTentativasEnvio(email.getTentativasEnvio() + 1);
                        formularioContatoRepo.save(email);
                    }
                }
            }
        }
    }
}
