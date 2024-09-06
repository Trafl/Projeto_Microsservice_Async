package com.empiricus.service_email.service;

import com.empiricus.service_email.domain.events.event.EmailCreated;
import com.empiricus.service_email.domain.exception.UsuarioOrEmailNotFound;
import com.empiricus.service_email.domain.model.Email;
import com.empiricus.service_email.domain.repository.EmailRepository;
import com.empiricus.service_email.domain.service.email.EmailServiceImpl;
import com.empiricus.service_email.domain.service.openfeign.UsuarioFeignService;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private EmailRepository repository;

    @Mock
    private UsuarioFeignService usuarioFeignService;

    @Mock
    private ApplicationEventPublisher publisher;

    Email email = new Email();

    @BeforeEach
    void setup(){
        email.setId(1L);
        email.setEmail("teste@email.com");
        email.setUsuario_id(1L);
        email.setData_criacao(LocalDate.of(2024,8,26));
    }

    @Nested
    class  getAllEmailsOfUsuario {

        @Test
        void when_getAllEmailsOfUsuario_Return_PageOfEmails(){
            var email2 = new Email();
            email2.setId(2L);
            email2.setEmail("teste@email2.com");
            email2.setUsuario_id(1L);
            email2.setData_criacao(LocalDate.of(2023,9,13));

            Pageable pageable = Pageable.ofSize(10);
            var listOfEmails = List.of(email, email2);

            Page<Email> pageOfEmails = new PageImpl<>(listOfEmails,pageable,listOfEmails.size());

            given(repository.getAllEmailsByUsuario_Id(anyLong(), any(Pageable.class))).willReturn(Optional.of(pageOfEmails));

            var result = emailService.getAllEmailsOfUsuario(1L, pageable);

            assertNotNull(result);
            assertTrue(result instanceof  Page<Email>);

            assertEquals(result.getContent().size(), 2);
            assertEquals(result.getContent().get(0).getEmail(),email.getEmail());
            assertEquals(result.getContent().get(1).getEmail(),email2.getEmail());

        }

        @Test
        void given_NonExistentUsuarioId_Throw_UsuarioOrEmailNotFound(){
            Pageable pageable = Pageable.ofSize(10);
            given(repository.getAllEmailsByUsuario_Id(anyLong(),any(Pageable.class))).willReturn(Optional.empty());

            var result = assertThrows(UsuarioOrEmailNotFound.class,
                    ()->{emailService.getAllEmailsOfUsuario(1L,pageable );});

            assertEquals("Não foi encontrado nenhum usuario de id: 1 associado a um ou mais emails", result.getMessage());
        }
    }

    @Nested
    class createEmail {

        @Test
        void given_EmailBody_When_createEmail_ReturnEmail(){
            var newEmail = new Email();
            newEmail.setId(1L);
            newEmail.setUsuario_id(email.getUsuario_id());
            newEmail.setEmail(email.getEmail());

            given(usuarioFeignService.usuarioExist(anyLong())).willReturn(true);
            given(repository.save(any(Email.class))).willReturn(newEmail);

            var result = emailService.createEmail(email);

            verify(publisher, times(1)).publishEvent(any(EmailCreated.class));
            verify(repository, times(1)).save(any(Email.class));

            assertTrue(result.getId() != null && result.getId() > 0);
            assertEquals(email.getEmail(),result.getEmail());
            assertEquals(email.getUsuario_id(),result.getUsuario_id());
            assertNotNull(result.getData_criacao());

        }
    }
}
