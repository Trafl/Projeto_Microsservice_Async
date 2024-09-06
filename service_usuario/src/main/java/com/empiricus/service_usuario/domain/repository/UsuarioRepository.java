package com.empiricus.service_usuario.domain.repository;

import com.empiricus.service_usuario.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.eh_admin = true")
    List<Usuario> findAdmins();
}
