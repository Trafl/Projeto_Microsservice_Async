package com.empiricus.service_usuario.service;

import com.empiricus.service_usuario.domain.exception.UsuarioExistException;
import com.empiricus.service_usuario.domain.exception.UsuarioNotFoundException;
import com.empiricus.service_usuario.domain.model.Usuario;
import com.empiricus.service_usuario.domain.repository.UsuarioRepository;
import com.empiricus.service_usuario.domain.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    Usuario usuario = new Usuario();

    @BeforeEach
    private void setup(){
        usuario.setId(1L);
        usuario.setNome("UsuarioTest");
        usuario.setCpf("123.456.789-99");
        usuario.setPassword("123");
        usuario.setData_criacao(LocalDate.of(2024, 9, 04));
        usuario.setEh_admin(true);
        usuario.setData_atualizacao(null);
    }

    @Nested
    class getAll {

        @Test
        void when_GetAll_Return_PageOfUsuarios(){
            Pageable pageable = Pageable.ofSize(10);
            Usuario usuario1 = new Usuario();
            usuario1.setNome("UsuarioTest1");
            usuario1.setCpf("xxx.456.xxx-99");
            usuario1.setPassword("321");
            usuario1.setData_criacao(LocalDate.of(2024, 10, 9));
            usuario1.setEh_admin(true);

            List listOfUsuarios = List.of(usuario, usuario1);
            Page<Usuario> pageOfUsuarios = new PageImpl<>(listOfUsuarios, pageable, listOfUsuarios.size());

            given(repository.findAll(pageable)).willReturn(pageOfUsuarios);

            var result = service.getAll(pageable);

            assertNotNull(result);
            assertTrue(result instanceof Page<Usuario>);
            assertEquals(result.getContent().size(), 2);
            assertEquals(result.getContent().get(0).getCpf(),usuario.getCpf());
            assertEquals(result.getContent().get(1).getCpf(),usuario1.getCpf());

        }
    }

    @Nested
    class getOne {

        @Test
        void given_Id_When_getOne_Return_UsuarioWithSameId(){
            given(repository.findById(anyLong())).willReturn(Optional.of(usuario));

            var result = service.getOne(anyLong());

            assertTrue(result.getId() != null && result.getId() > 0);

            assertEquals(usuario.getId(), result.getId());
            assertEquals(usuario.getNome(), result.getNome());
            assertEquals(usuario.getCpf(), result.getCpf());
            assertEquals(usuario.getPassword(), result.getPassword());
            assertEquals(usuario.getData_criacao(), result.getData_criacao());
            assertEquals(usuario.getEh_admin(), result.getEh_admin());
            assertEquals(usuario.getData_atualizacao(), result.getData_atualizacao());

        }

        @Test
        void given_WrongId_When_getOne_ThrowUsuarioNotFoundException(){
            given(repository.findById(anyLong())).willReturn(Optional.empty());

            var result = assertThrows(UsuarioNotFoundException.class,
                    () -> {service.getOne(1L);
            });

            assertEquals("Usuario de id 1 não foi encontrado no banco de dados", result.getMessage());
        }
    }

    @Nested
    class addUser {

        @Test
        void given_UsuarioBody_When_addUser_Return_Usuario(){
            Usuario novoUsuario = new Usuario();
            novoUsuario.setId(1L);
            novoUsuario.setNome(usuario.getNome());
            novoUsuario.setCpf(usuario.getCpf());
            novoUsuario.setPassword(usuario.getPassword());
            novoUsuario.setData_criacao(usuario.getData_criacao());
            novoUsuario.setEh_admin(usuario.getEh_admin());

            given(repository.save(any(Usuario.class))).willReturn(novoUsuario);

            var result = service.addUsuario(usuario);

            assertTrue(result.getId() != null && result.getId() > 0);

            verify(repository, times(1)).save(any(Usuario.class));

            assertEquals(novoUsuario.getId(), result.getId());
            assertEquals(novoUsuario.getNome(), result.getNome());
            assertEquals(novoUsuario.getCpf(), result.getCpf());
            assertEquals(novoUsuario.getPassword(), result.getPassword());
            assertEquals(novoUsuario.getData_criacao(), result.getData_criacao());
            assertEquals(novoUsuario.getEh_admin(), result.getEh_admin());
            assertEquals(novoUsuario.getData_criacao(), result.getData_criacao());

        }

        @Test
        void given_ExistentUserName_When_AddUser_ThrowUsuarioExistException(){

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("Teste");

            given(repository.existsByNome(anyString())).willReturn(true);

            var result = assertThrows(UsuarioExistException.class,
                    () -> {service.addUsuario(novoUsuario);
                    });

            verify(repository, never()).save(any(Usuario.class));
            assertEquals("Usuario com o nome Teste já existe", result.getMessage());

        }

    }

    @Nested
    class updateUser {

        @Test
        void given_IdAndUsuarioBody_WhenUpdateUser_ReturnUpdatedUsuario(){

            Usuario usuarioAtualizado = new Usuario();
            usuarioAtualizado.setNome("Usuario Atualizado");
            usuarioAtualizado.setCpf("xxx.xxx.xx-xx");
            usuarioAtualizado.setPassword("4567");

            given(repository.findById(anyLong())).willReturn(Optional.of(usuario));

            var result = service.updateUsuario(usuarioAtualizado, 1L);

            assertNotNull(result);
            assertEquals(usuarioAtualizado.getNome(), result.getNome());
            assertEquals(usuarioAtualizado.getCpf(), result.getCpf());
            assertNotNull(result.getData_atualizacao());
            assertEquals(usuario.getId(), result.getId());
        }

        @Test
        void given_BodyWithNullFields_WhenUpdateUser(){

            Usuario usuarioAtualizado = new Usuario();
            usuarioAtualizado.setNome("");
            usuarioAtualizado.setCpf("");
            usuarioAtualizado.setPassword("");

            given(repository.findById(anyLong())).willReturn(Optional.of(usuario));

            var result = service.updateUsuario(usuarioAtualizado, 1L);

            assertNotNull(result);
            assertEquals(usuario.getNome(), result.getNome());
            assertEquals(usuario.getCpf(), result.getCpf());
            assertEquals(usuario.getPassword(), result.getPassword());
            assertNotNull(result.getData_atualizacao());
            assertEquals(usuario.getId(), result.getId());
        }



        @Test
        void given_WrongId_WhenUpdateUser_ThrowUsuarioNotFoundException(){

            Usuario usuario1 = new Usuario();
            given(repository.findById(anyLong())).willReturn(Optional.empty());

            var result = assertThrows(UsuarioNotFoundException.class,
                    () -> {service.updateUsuario(usuario1,1L);
                    });

            assertEquals("Usuario de id 1 não foi encontrado no banco de dados", result.getMessage());

        }

        @Test
        void given_ExistentUserName_WhenUpdateUser_ThrowUsuarioExistException(){

            Usuario usuario1 = new Usuario();
            usuario1.setNome("Teste");

            given(repository.existsByNome(anyString())).willReturn(true);

            var result = assertThrows(UsuarioExistException.class,
                    () -> {service.updateUsuario(usuario1,1L);
                    });
            assertEquals("Usuario com o nome Teste já existe", result.getMessage());

        }

    }

    @Nested
    class deleteUser {

        @Test
        void given_UsuarioId_When_deleteUser_ReturnVoid(){
            given(repository.findById(anyLong())).willReturn(Optional.of(usuario));

            service.deleteUsuario(anyLong());

            verify(repository, times(1)).deleteById(anyLong());
        }

        @Test
        void given_WrongClientId_When_deleteClient_ThrowClientNotFoundException(){
            given(repository.findById(1L)).willReturn(Optional.empty());

            var result = assertThrows(UsuarioNotFoundException.class,
                    ()-> {service.deleteUsuario(1L);
            });

            verify(repository, never()).deleteById(1L);
            assertEquals("Usuario de id 1 não foi encontrado no banco de dados", result.getMessage());
        }

    }
}
