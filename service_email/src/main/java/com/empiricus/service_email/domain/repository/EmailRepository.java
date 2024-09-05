package com.empiricus.service_email.domain.repository;

import com.empiricus.service_email.domain.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Page<Email>> getAllEmailsByUsuarioId(Long usuarioId, Pageable pageable);

}
