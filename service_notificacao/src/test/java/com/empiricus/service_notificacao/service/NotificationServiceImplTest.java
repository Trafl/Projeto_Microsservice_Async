package com.empiricus.service_notificacao.service;

import com.empiricus.service_notificacao.domain.exception.UsuarioNotFoundException;
import com.empiricus.service_notificacao.domain.model.Email;
import com.empiricus.service_notificacao.domain.model.Usuario;
import com.empiricus.service_notificacao.domain.service.feing.EmailFeignService;
import com.empiricus.service_notificacao.domain.service.feing.UsuarioFeignService;
import com.empiricus.service_notificacao.domain.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationServiceImplTest {

    @InjectMocks
    NotificationServiceImpl service;

    @Mock
    JavaMailSender mailSender;

    @Mock
    UsuarioFeignService usuarioFeignService;

    @Mock
    EmailFeignService emailFeignService;

    Email email;

    Usuario usuario;

    @BeforeEach
    void setUp() {
        email = new Email();
        email.setEmail("teste@example.com");
        email.setUsuario_id(1L);
        usuario = new Usuario();
        usuario.setCpf("12345678900");
    }

    @Test
    void when_NotifyAdminsEmailCreated_Then_NotifyAllAdmins(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willReturn(usuario);
        given(emailFeignService.getAdmins()).willReturn(List.of("admin@email.com", "admin2@email.com"));

        service.notifyAdminsEmailCreated(email);

        verify(mailSender, times(2)).send(any(SimpleMailMessage.class));
    }


    @Test
    void when_NotifyAdminsEmailDeleted_Then_NotifyAllAdmins(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willReturn(usuario);
        given(emailFeignService.getAdmins()).willReturn(List.of("admin@email.com", "admin2@email.com"));

        service.notifyAdminsEmailDeleted(email);

        verify(mailSender, times(2)).send(any(SimpleMailMessage.class));
    }

    @Test
    void when_NotifyAdminsEmailCreated_For_ListOf1Admins_SendOneEmail(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willReturn(usuario);
        given(emailFeignService.getAdmins()).willReturn(List.of("admin@email.com"));

        service.notifyAdminsEmailCreated(email);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void when_NotifyAdminsEmailCreated_For_ListOf0Admins_SendOneEmail(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willReturn(usuario);
        given(emailFeignService.getAdmins()).willReturn(List.of());

        service.notifyAdminsEmailCreated(email);

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void when_notifyAdminsEmailCreated_ExecutionException() {
        given(usuarioFeignService.getOneUsuario(1L)).willThrow(new RuntimeException("Erro ao buscar usuário"));

        service.notifyAdminsEmailCreated(email);

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void given_NonExistUsuarioId_When_NotifyAdminsEmailCreated_ThrowUsuarioNotFoundException(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willThrow(new UsuarioNotFoundException("Usuário não encontrado"));

        service.notifyAdminsEmailCreated(email);

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void given_NonExistUsuarioId_when_NotifyAdminsEmailDeleted_ThrowUsuarioNotFoundException(){
        given(usuarioFeignService.getOneUsuario(anyLong())).willThrow(new UsuarioNotFoundException("Usuário não encontrado"));

        service.notifyAdminsEmailDeleted(email);

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

}
