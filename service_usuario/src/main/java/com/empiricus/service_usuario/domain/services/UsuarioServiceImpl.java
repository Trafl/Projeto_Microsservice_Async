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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository repository;
    
    private final String logTime = LocalDateTime.now().toString();

    @Override
    public Page<Usuario> getAll(Pageable pageable) {
        log.info("[{}] - [UsuarioImpl] - executando getAll()", logTime);
        return repository.findAll(pageable);
    }

    @Override
    public Usuario getOne(Long id) {
        log.info("[{}] - [UsuarioImpl] - executando getOne() id: {}", logTime, id);
        return repository.findById(id).orElseThrow(
                () -> {
                  return new UsuarioNotFoundException(
                          String.format("Usuario de id %d não foi encontrado no banco de dados", id));
                }
        );
    }

    @Override
    public Usuario addUser(Usuario usuario) {
        log.info("[{}] - [UsuarioImpl] - executando addUser()", logTime);
        var novoUsuario = repository.save(usuario);
        log.info("[{}] - [UsuarioImpl] - Usuario adicionado com sucesso id: {}", logTime, novoUsuario.getId());
        return novoUsuario;
    }

    @Override
    @Transactional
    public Usuario updateUser(Usuario usuarioAtualizado, Long id) {
        log.info("[{}] - [UsuarioImpl] - executando updateUser() id: {}", logTime, id);
        var usuarioSalvo = getOne(id);

        updateUsuario(usuarioAtualizado, usuarioSalvo);

        usuarioSalvo.setData_atualizacao(LocalDate.now());

        log.info("[{}] - [UsuarioImpl] - Usuario Atualizado com sucesso id: {}", logTime, usuarioSalvo.getId());
        return usuarioSalvo;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("[{}] - [UsuarioImpl] - executando deleteUser() id: {}", logTime, id);
        getOne(id);
        repository.deleteById(id);
        log.info("[{}] - [UsuarioImpl] - Usuario deletado com sucesso id: {}", logTime, id);
    }

    private void updateUsuario(Usuario usuarioAtualizado, Usuario usuarioSalvo) {

        if(usuarioAtualizado.getNome() != null && !usuarioAtualizado.getNome().isBlank()){
            usuarioSalvo.setNome(usuarioAtualizado.getNome());
        }
        if(usuarioAtualizado.getCpf() != null && !usuarioAtualizado.getCpf().isBlank()){
            usuarioSalvo.setCpf(usuarioAtualizado.getCpf());
        }
        if(usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().isBlank()){
            usuarioSalvo.setPassword(usuarioAtualizado.getPassword());
        }
    }
}
