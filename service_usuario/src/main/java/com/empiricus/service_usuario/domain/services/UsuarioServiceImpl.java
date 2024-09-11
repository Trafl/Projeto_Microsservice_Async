package com.empiricus.service_usuario.domain.services;

import com.empiricus.service_usuario.domain.exception.UsuarioNotFoundException;
import com.empiricus.service_usuario.domain.model.Usuario;
import com.empiricus.service_usuario.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<Usuario> getAll(Pageable pageable) {
        log.info("[{}] - [UsuarioServiceImpl] - executando getAll()", LocalDateTime.now());
        return repository.findAll(pageable);
    }

    @Override
    public Usuario getOne(Long id) {
        log.info("[{}] - [UsuarioServiceImpl] - executando getOne() id: {}", LocalDateTime.now(), id);
        return repository.findById(id).orElseThrow(
                () -> {
                  return new UsuarioNotFoundException(
                          String.format("Usuario de id %d não foi encontrado no banco de dados", id));
                }
        );
    }

    @Override
    public Usuario addUser(Usuario usuario) {
        log.info("[{}] - [UsuarioServiceImpl] - executando addUser()", LocalDateTime.now());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        var novoUsuario = repository.save(usuario);
        log.info("[{}] - [UsuarioServiceImpl] - Usuario adicionado com sucesso id: {}", LocalDateTime.now(), novoUsuario.getId());
        return novoUsuario;
    }

    @Override
    @Transactional
    public Usuario updateUser(Usuario usuarioAtualizado, Long id) {
        log.info("[{}] - [UsuarioServiceImpl] - executando updateUser() id: {}", LocalDateTime.now(), id);
        var usuarioSalvo = getOne(id);

        updateUsuario(usuarioAtualizado, usuarioSalvo);

        usuarioSalvo.setData_atualizacao(LocalDate.now());

        log.info("[{}] - [UsuarioServiceImpl] - Usuario Atualizado com sucesso id: {}", LocalDateTime.now(), usuarioSalvo.getId());
        return usuarioSalvo;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("[{}] - [UsuarioServiceImpl] - executando deleteUser() id: {}", LocalDateTime.now(), id);
        getOne(id);
        repository.deleteById(id);
        log.info("[{}] - [UsuarioServiceImpl] - Usuario deletado com sucesso id: {}", LocalDateTime.now(), id);
    }

    @Override
    public boolean usuarioExist(Long id) {
        log.info("[{}] - [UsuarioServiceImpl] - executando usuarioExist() id: {}", LocalDateTime.now(), id);
        return repository.existsById(id);
    }

    public List<Usuario> getAdmins(){
        log.info("[{}] - [UsuarioServiceImpl] - executando findAdmins()", LocalDateTime.now());
        return repository.findAdmins();
    }

    private void updateUsuario(Usuario usuarioAtualizado, Usuario usuarioSalvo) {

        if(usuarioAtualizado.getNome() != null && !usuarioAtualizado.getNome().isBlank()){
            usuarioSalvo.setNome(usuarioAtualizado.getNome());
        }
        if(usuarioAtualizado.getCpf() != null && !usuarioAtualizado.getCpf().isBlank()){
            usuarioSalvo.setCpf(usuarioAtualizado.getCpf());
        }
    }
}
