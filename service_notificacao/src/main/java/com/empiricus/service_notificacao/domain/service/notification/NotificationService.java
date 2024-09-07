package com.empiricus.service_notificacao.domain.service.notification;

import com.empiricus.service_notificacao.domain.model.Email;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    CompletableFuture<Void> notifyAdminsEmailCreated(Email email);

    CompletableFuture<Void> notifyAdminsEmailDeleted(Email email);
}
