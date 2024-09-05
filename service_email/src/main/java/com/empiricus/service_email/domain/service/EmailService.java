package com.empiricus.service_email.domain.service;

import com.empiricus.service_email.domain.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {

    Page<Email> getAllEmailsOfUsuario(Long usuarioId, Pageable pageable);

    Email createEmail(Email email);

    void deleteEmail(Long id);
}
