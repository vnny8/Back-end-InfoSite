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

    public void cadastrar(CadastroFormularioContatoDto dto){
        try {
//            SimpleMailMessage mensagemCliente = new SimpleMailMessage();
//            SimpleMailMessage mensagemInfo = new SimpleMailMessage();
//            mensagemInfo.setFrom("juancassemirotestes@gmail.com");
//            mensagemInfo.setTo("juancassemirotestes@gmail.com");
//            mensagemInfo.setSubject("Mensagem de Contato: "+dto.nome());
//            mensagemInfo.setText("Nome: "+dto.nome()+"\nE-mail: "+dto.email()+"\nMensagem: "+dto.assunto());
//            mensagemCliente.setFrom("juancassemirotestes@gmail.com");
//            mensagemCliente.setTo(dto.email());
//            mensagemCliente.setSubject(dto.nome() +" - Recebemos sua mensagem!");
//            mensagemCliente.setText("<h2>Olá "+dto.nome()+"</h2>, nós recebemos sua mensagem e nossa equipe entrará em contato para verificar sua solicitação!");
//
//            emailSender.send(mensagemInfo);
//            emailSender.send(mensagemCliente);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            String html = "<h2>Olá "+dto.nome()+",</h2> Recebemos sua mensagem!";
            helper.setTo(dto.email());
            helper.setSubject(dto.assunto());
            helper.setText(html, true);
            emailSender.send(message);
        }catch(MessagingException e){
            throw new ValidacaoException(e.getMessage());

        }finally{
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
