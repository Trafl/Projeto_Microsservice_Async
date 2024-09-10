package com.empiricus.service_email.domain.repository;

import com.empiricus.service_email.domain.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("SELECT e FROM Email e WHERE e.usuario_id = :usuario_id")
    Page<Email> getAllEmailsByUsuario_Id(@Param("usuario_id") Long usuario_id, Pageable pageable);

    @Query("SELECT e.email FROM Email e WHERE e.eh_admin = true")
    List<String> getEmailAdmin();

    @Query("SELECT COUNT(e) > 0 FROM Email e WHERE e.usuario_id = :usuario_id")
    Boolean existEmailOfUsuario_id(@Param("usuario_id") Long usuario_id);
}
