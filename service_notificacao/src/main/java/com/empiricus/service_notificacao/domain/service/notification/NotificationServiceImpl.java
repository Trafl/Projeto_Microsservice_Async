package com.empiricus.service_notificacao.domain.service.notification;

import com.empiricus.service_notificacao.domain.model.Email;
import com.empiricus.service_notificacao.domain.service.feing.EmailFeignService;
import com.empiricus.service_notificacao.domain.service.feing.UsuarioFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    private final UsuarioFeignService usuarioFeignService;

    private final EmailFeignService emailFeignService;

    @Async
    @Override
    @KafkaListener(topics = "email-created")
    public CompletableFuture<Void> notifyAdminsEmailCreated(Email email) {
        log.info("[{}] - [NotificationServiceImpl] - executando notifyAdminsEmailCreated()", LocalDateTime.now());

        return usuarioFeignService.getOneUsuario(email.getUsuario_id())
                .thenApply(usuario -> {

                    String subject = String.format("O email %s foi criado/alterado para o" +
                            "usuário de CPF %s", email.getEmail(), usuario.getCpf());

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

                    sendEmailsForAdmins(subject, body);
                    return null;
                });
    }

    @Async
    @Override
    @KafkaListener(topics = "email-deleted")
    public CompletableFuture<Void> notifyAdminsEmailDeleted(Email email) {
        log.info("[{}] - [NotificationServiceImpl] - executando notifyAdminsEmailDeleted()", LocalDateTime.now());

        return usuarioFeignService.getOneUsuario(email.getUsuario_id()).thenApply(
            usuario -> {
                String subject = String.format("O email %s foi criado/alterado para o" +
                        "usuário de CPF %s", email.getEmail(),usuario.getCpf());

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

                sendEmailsForAdmins(subject, body);
                return null;
            });
    }

    @Async
    private CompletableFuture<Void> sendEmailsForAdmins(String subject, String body) {

        log.info("[{}] - [NotificationServiceImpl] - executando sendEmailsForAdmins()", LocalDateTime.now());

        return findAdminEmails().thenAccept(adminEmails -> {
            for (String adminEmail : adminEmails) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(adminEmail);
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);

                log.info("[{}] - [NotificationServiceImpl] - Email enviado para o administrador: {}", LocalDateTime.now(), adminEmail);
            }
        });
    }

    @Async
    private CompletableFuture<List<String>>  findAdminEmails(){

        return emailFeignService.getAdmins()
                .thenApply(adminEmails -> {
                    log.info("[{}] - [NotificationServiceImpl] - Lista de emails dos administradores recebida: {}", LocalDateTime.now(), adminEmails);
                    return adminEmails;
                });

        // chamada na API de Email, assincronalmente e recuperar so os Emails dos adm's
    }
}
