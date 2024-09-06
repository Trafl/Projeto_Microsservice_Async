package com.empiricus.service_email.service;

import com.empiricus.service_email.domain.repository.EmailRepository;
import com.empiricus.service_email.domain.service.email.EmailServiceImpl;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private EmailRepository repository;

    @Nested
    class  getAllEmailsOfUsuario {

    }
}
