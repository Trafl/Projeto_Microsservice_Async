package com.empiricus.service_usuario.domain.services;

import com.empiricus.service_usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    public Page<Usuario> getAll(Pageable pageable);

    public Usuario getOne(Long id);

    public Usuario addUser(Usuario usuario);

    public Usuario updateUser(Usuario usuario, Long id);

    public void deleteUser(Long id);


}
