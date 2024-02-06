package com.ValidaAPI.Projeto.service;

import com.ValidaAPI.Projeto.dto.CadastroFormularioContatoDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailInfo;

    public void enviarEmails(CadastroFormularioContatoDto dto) throws MessagingException, IOException {
            MimeMessage emailCliente = emailSender.createMimeMessage();
            MimeMessage emailInterno = emailSender.createMimeMessage();

            MimeMessageHelper helperCliente = new MimeMessageHelper(emailCliente, true, "UTF-8");
            MimeMessageHelper helperInterno = new MimeMessageHelper(emailInterno, true, "UTF-8");

            String htmlCliente = lerConteudoHTML("src/main/resources/static/MsgCliente.html");
            String htmlInterno = lerConteudoHTML("src/main/resources/static/MsgInterna.html");

            htmlCliente = htmlCliente.replace("{{nome}}", dto.nome());

            htmlInterno = htmlInterno.replace("{{nome}}", dto.nome()).replace("{{email}}", dto.email()).replace("{{assunto}}", dto.assunto()).replace("{{telefone}}", dto.telefone());

            helperCliente.setText(htmlCliente, true);
            helperCliente.setTo(dto.email());
            helperCliente.setSubject("Recebemos sua mensagem \uD83D\uDCE9");

            helperInterno.setText(htmlInterno, true);

            helperInterno.setTo(emailInfo);
            helperInterno.setSubject("Mensagem recebida do site");

            emailSender.send(emailCliente);
            emailSender.send(emailInterno);

    }

    private String lerConteudoHTML(String caminho) throws IOException {
        Path path = Paths.get(caminho);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

}
