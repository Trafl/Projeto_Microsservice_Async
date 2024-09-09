package com.empiricus.service_email.domain.service.email;

import com.empiricus.service_email.domain.events.event.EmailCreated;
import com.empiricus.service_email.domain.events.event.EmailDeleted;
import com.empiricus.service_email.domain.exception.UsuarioNotFoundException;
import com.empiricus.service_email.domain.exception.EmailNotFoundException;
import com.empiricus.service_email.domain.model.Email;
import com.empiricus.service_email.domain.repository.EmailRepository;
import com.empiricus.service_email.domain.service.openfeign.UsuarioFeignService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ApplicationEventPublisher publisher;

    private final EmailRepository repository;

    private final UsuarioFeignService usuarioFeignService;

    @Override
    public Page<Email> getAllEmailsOfUsuario(Long usuarioId, Pageable pageable) {
        log.info("[{}] - [EmailServiceImpl] - executando getAllEmailsOfUsuario(), usuario de id: {}", LocalDateTime.now(), usuarioId);

        var emailsPage = repository.getAllEmailsByUsuario_Id(usuarioId, pageable)
                .orElseThrow(()-> new EmailNotFoundException(
                        String.format("Não foi encontrado nenhum usuario de id: %d associado a um ou mais emails",usuarioId)));

        return emailsPage;
    }

    @Override
    @Transactional
    public Email createEmail(Email email) {

        var usuario =usuarioFeignService.getUsuario(email.getUsuario_id());

        if(usuario.getStatusCode() == HttpStatusCode.valueOf(404)){
            throw new UsuarioNotFoundException(
                  String.format("Não foi encontrado nenhum usuario com o id: %d",email.getUsuario_id()));
        }

        email.setEh_admin(usuario.getBody().getEh_admin());
        log.info("[{}] - [EmailServiceImpl] - executando createEmail(), usuario de id: {}", LocalDateTime.now(), email.getUsuario_id());
        var saveEmail = repository.save(email);
        log.info("[{}] - [EmailServiceImpl] - Email salvo com sucesso para usuario de id: {}", LocalDateTime.now(), email.getUsuario_id());

        publisher.publishEvent(new EmailCreated(saveEmail));

        return saveEmail;
    }

    @Override
    @Transactional
    public void deleteEmail(Long id) {
        log.info("[{}] - [EmailServiceImpl] - Executando deleteEmail(), usuario de id: {}", LocalDateTime.now(), id);

        var email= repository.findById(id).orElseThrow(()-> new EmailNotFoundException(
                String.format("Não foi encontrado nenhum email com esse id: %d", id)));

        repository.deleteById(id);

        log.info("[{}] - [EmailServiceImpl] - Email deletado com sucesso, usuario de id: {}", LocalDateTime.now(), id);

        publisher.publishEvent(new EmailDeleted(email));
    }

    @Override
    public List<String> getAllEmailsAdmins() {
        log.info("[{}] - [EmailServiceImpl] - executando getAllEmailsAdmins()", LocalDateTime.now());
        return repository.getEmailAdmin();
    }
}
