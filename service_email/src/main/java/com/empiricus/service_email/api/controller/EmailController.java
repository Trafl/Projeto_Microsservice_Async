package com.empiricus.service_email.api.controller;

import com.empiricus.service_email.api.mapper.EmailMapper;
import com.empiricus.service_email.domain.dto.EmailDTOInput;
import com.empiricus.service_email.domain.dto.EmailDTOOutput;
import com.empiricus.service_email.domain.service.email.EmailService;
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

@Log4j2
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailMapper mapper;

    private final EmailService emailService;

    private final PagedResourcesAssembler<EmailDTOOutput> pagedResourcesAssembler;

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<PagedModel<EntityModel<EmailDTOOutput>>>
    getAllEmailsForUsuarioId(@PathVariable Long usuario_id, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){

        log.info("[{}] - [EmailController] IP: {}, Request: GET, EndPoint: '/api/email/usuario/{}'", LocalDateTime.now(), request.getRemoteAddr(), usuario_id);

        var pageEmails = emailService.getAllEmailsOfUsuario(usuario_id, pageable);
        var pageEmailsDto = mapper.toPageDTO(pageEmails);

        var pagedModel = pagedResourcesAssembler.toModel(pageEmailsDto);

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping()
    ResponseEntity<EmailDTOOutput> createEmail(@RequestBody @Valid EmailDTOInput input, HttpServletRequest request){

        log.info("[{}] - [EmailController] IP: {}, Request: POST, EndPoint: '/api/email'", LocalDateTime.now(), request.getRemoteAddr());

        var email = mapper.toModel(input);
        var savedEmail = emailService.createEmail(email);
        var emailDto = mapper.toDto(savedEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(emailDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmail(@PathVariable Long id, HttpServletRequest request){
        log.info("[{}] - [EmailController] IP: {}, Request: DELETE, EndPoint: '/api/email/{}'", LocalDateTime.now(), request.getRemoteAddr(),id);

        emailService.deleteEmail(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/admins")
    public ResponseEntity<List<String>> getEmailsOfAdmins( HttpServletRequest request){
        log.info("[{}] - [EmailController] IP: {}, Request: GET, EndPoint: '/api/email/admins'", LocalDateTime.now(), request.getRemoteAddr());
        var emails = emailService.getAllEmailsAdmins();

        return ResponseEntity.ok(emails);
    }
}
