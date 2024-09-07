package com.empiricus.service_email.domain.service.email;

import com.empiricus.service_email.domain.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailService {

    Page<Email> getAllEmailsOfUsuario(Long usuarioId, Pageable pageable);

    public List<String> getAllEmailsAdmins(List<Long> usuario_ids);

    Email createEmail(Email email);

    void deleteEmail(Long id);
}
