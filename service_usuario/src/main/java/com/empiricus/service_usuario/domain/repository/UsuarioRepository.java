package com.empiricus.service_usuario.domain.repository;

import com.empiricus.service_usuario.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.eh_admin = true")
    List<Usuario> findAdmins();

    boolean existsByNome(String nome);

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome")
    Optional<Usuario> findByNomeAndPassword(@Param("nome") String nome);
}
