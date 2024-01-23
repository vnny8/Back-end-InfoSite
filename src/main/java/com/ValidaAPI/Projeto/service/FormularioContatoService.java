package com.ValidaAPI.Projeto.service;
import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import com.ValidaAPI.Projeto.dto.FormularioContatoDto;
import com.ValidaAPI.Projeto.model.FormularioContato;
import com.ValidaAPI.Projeto.repository.FormularioContatoRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.ValidaAPI.Projeto.validacao.ValidacaoException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FormularioContatoService {

    @Autowired
    private FormularioContatoRepository formularioContatoRepo;

    @Autowired
    private JavaMailSender emailSender;
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

    public Optional<FormularioContato> listarPorId(Long id){
        return formularioContatoRepo.findById(id);
    }

    public String lerConteudoHTML(String caminho) throws IOException {
        Path path = Paths.get(caminho);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    public void cadastrar(CadastroFormularioContatoDto dto){
        try {
            MimeMessage emailCliente = emailSender.createMimeMessage();
            MimeMessage emailInterno = emailSender.createMimeMessage();

            MimeMessageHelper helperCliente = new MimeMessageHelper(emailCliente,true,"UTF-8");
            MimeMessageHelper helperInterno = new MimeMessageHelper(emailInterno,true,"UTF-8");

            String htmlCliente = lerConteudoHTML("src/main/resources/static/MsgCliente.html");
            String htmlInterno = lerConteudoHTML("src/main/resources/static/MsgInterna.html");

            htmlCliente = htmlCliente.replace("{{nome}}",dto.nome());

            htmlInterno = htmlInterno.replace("{{nome}}",dto.nome()).replace("{{email}}",dto.email()).replace("{{assunto}}",dto.assunto()).replace("{{telefone}}",dto.telefone());

            helperCliente.setText(htmlCliente,true);
            helperCliente.setTo(dto.email());
            helperCliente.setSubject("Recebemos sua mensagem \uD83D\uDCE9");

            helperInterno.setText(htmlInterno,true);
            helperInterno.setTo("juancassemirotestes@gmail.com");
            helperInterno.setSubject("Mensagem recebida do site");

            emailSender.send(emailCliente);
            emailSender.send(emailInterno);

        }catch(MessagingException | IOException e){
            throw new ValidacaoException(e.getMessage());

        }
        finally{
            formularioContatoRepo.save(new FormularioContato(dto));
        }

    }

    public void deletarPorId(Long id){
        if(!formularioContatoRepo.existsById(id)){
            throw new ValidacaoException("Formulário não encontrado com o id informado");
        }
        formularioContatoRepo.deleteById(id);
    }


}
