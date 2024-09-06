package com.empiricus.service_usuario.domain.repository;

import com.empiricus.service_usuario.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existById(Long id);
}
