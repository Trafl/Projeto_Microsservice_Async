package com.empiricus.service_email.domain.service;

import com.empiricus.service_email.domain.model.Email;
import com.empiricus.service_email.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.cglib.core.Local;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService{

    private final JavaMailSender mailSender;

    @Override
    public void notifyAdminsEmailCreated(Email email) {
        log.info("[{}] - [NotificationServiceImpl] - executando notifyAdminsEmailCreated()", LocalDateTime.now());

        // chama sincrona no serviço de usuario OpenFeing(email.getUsuario_id)
        Usuario usuario = new Usuario();

        String subject = String.format("O email %s foi criado/alterado para o" +
                "usuário de CPF %d", email.getEmail(),usuario.getCpf());

        String body = String.format(
                "Prezado Administrador,%n%n" +
                        "Informamos que um novo email foi criado no sistema.%n%n" +
                        "Detalhes do Email:%n" +
                        "- Endereço de Email: %s%n" +
                        "- CPF do Usuário: %s%n%n" +
                        "Data de Criação: %s%n%n" +
                        "Atenciosamente,%n" +
                        "Sistema de Gestão de Emails",
                email.getEmail(),
                usuario.getCpf(),
                email.getData_criacao()
        );

        enviarEmailParaAdmins(subject, body);
    }

    @Override
    public void notifyAdminsEmailDeleted(Email email) {
        log.info("[{}] - [NotificationServiceImpl] - executando notifyAdminsEmailDeleted()", LocalDateTime.now());

        // chama sincrona no serviço de usuario OpenFeing(email.getUsuario_id)
        Usuario usuario = new Usuario();

        String subject = String.format("O email %s foi criado/alterado para o" +
                "usuário de CPF %d", email.getEmail(),usuario.getCpf());

        String body = String.format(
                "Prezado Administrador,%n%n" +
                        "Informamos que um email foi deletado do sistema.%n%n" +
                        "Detalhes do Email Deletado:%n" +
                        "- CPF do Usuário: %s%n%n" +
                        "Data da Deleção: %s%n%n" +
                        "Atenciosamente,%n" +
                        "Sistema de Gestão de Emails",
                usuario.getCpf(),
                LocalDateTime.now()
        );

        enviarEmailParaAdmins(subject, body);

    }

    @Async
    private void enviarEmailParaAdmins(String subject, String body) {

        List<String> adminEmails = findAdminEmails();

        for (String adminEmail : adminEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

            log.info("[{}] - [NotificationServiceImpl] - Email enviado", LocalDateTime.now());
        }

    }

    private List<String> findAdminEmails(){
        //chamada no kafka para pegar id de usuarios administradores

        //chamada no serviço de email para recuperar o email de admin e depois enviar

        return null;
    }
}
