package com.empiricus.service_usuario.domain.services;

import com.empiricus.service_usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    public Page<Usuario> getAll(Pageable pageable);

    public Usuario getOne(Long id);

    public Usuario addUsuario(Usuario usuario);

    public Usuario updateUsuario(Usuario usuario, Long id);

    public void deleteUsuario(Long id);

    public boolean usuarioExist(Long id);

    public List<Usuario> getAdmins();
}
