package com.empiricus.service_usuario.api.controller;

import com.empiricus.service_usuario.api.usuario_mapper.UsuarioMapper;
import com.empiricus.service_usuario.domain.dto.UsuarioDTOInput;
import com.empiricus.service_usuario.domain.dto.UsuarioDTOOutput;
import com.empiricus.service_usuario.domain.dto.UsuarioUpdateDTOInput;
import com.empiricus.service_usuario.domain.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Log4j2
public class UsuarioController {

    private final UsuarioMapper mapper;

    private final UsuarioService service;

    private final PagedResourcesAssembler<UsuarioDTOOutput> pagedResourcesAssembler;


    @GetMapping
    ResponseEntity<PagedModel<EntityModel<UsuarioDTOOutput>>> getAll(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: GET, EndPoint: '/api/usuario'", LocalDateTime.now(), request.getRemoteAddr());
        var pageUsuario = service.getAll(pageable);
        var pageUsuarioDTO = mapper.toPageDto(pageUsuario);

        var pagedModel = pagedResourcesAssembler.toModel(pageUsuarioDTO);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/admins")
    ResponseEntity<List<UsuarioDTOOutput>> getAdmins(HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: GET, EndPoint: '/api/usuario/admins'", LocalDateTime.now(), request.getRemoteAddr());

        //Enviar uma List de Id's de usuario ADM

        var pageUsuario = service.getAdmins();
        var listUsuarioDTO = mapper.toListDto(pageUsuario);

        return ResponseEntity.ok(listUsuarioDTO);
    }

    @GetMapping("/{usuarioId}")
    ResponseEntity<UsuarioDTOOutput> getOne(@PathVariable Long usuarioId, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: GET, EndPoint: '/api/usuario/{}'", LocalDateTime.now(), request.getRemoteAddr(), usuarioId);
        var usuario = service.getOne(usuarioId);
        var usuarioDto = mapper.toDTO(usuario);

        return ResponseEntity.ok(usuarioDto);
    }

    @PostMapping
    ResponseEntity<UsuarioDTOOutput> addUser(@RequestBody @Valid UsuarioDTOInput input, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: POST, EndPoint: '/api/usuario'", LocalDateTime.now(), request.getRemoteAddr());
        var usuarioInput = mapper.toModel(input);
        var usuario = service.addUser(usuarioInput);
        var usuarioDto = mapper.toDTO(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }

    @PutMapping("/{usuarioId}")
    ResponseEntity<UsuarioDTOOutput> updateUser(@PathVariable Long usuarioId, @RequestBody UsuarioUpdateDTOInput input, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: PUT, EndPoint: '/api/usuario/{}'", LocalDateTime.now(), request.getRemoteAddr(), usuarioId);
        var usuarioInput = mapper.toModelUpdate(input);
        var usuario = service.updateUser(usuarioInput, usuarioId);
        var usuarioDto = mapper.toDTO(usuario);

        return ResponseEntity.ok(usuarioDto);
    }

    @DeleteMapping("/{usuarioId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long usuarioId, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: DELETE, EndPoint: '/api/usuario/{}'", LocalDateTime.now(), request.getRemoteAddr(), usuarioId);
        service.deleteUser(usuarioId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{usuarioId}/exist")
    ResponseEntity<Boolean> usuarioExist(@PathVariable Long usuarioId, HttpServletRequest request){
        log.info("[{}] - [UsuarioController] IP: {}, Request: GET, EndPoint: '/api/usuario/{}/exist'", LocalDateTime.now(), request.getRemoteAddr(), usuarioId);
        var exist = service.usuarioExist(usuarioId);

        return ResponseEntity.ok(exist);
    }

}
