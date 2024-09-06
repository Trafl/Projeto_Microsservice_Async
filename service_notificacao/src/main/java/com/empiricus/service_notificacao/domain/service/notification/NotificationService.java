package com.empiricus.service_notificacao.domain.service.notification;

import com.empiricus.service_notificacao.domain.model.Email;

public interface NotificationService {

    public void notifyAdminsEmailCreated(Email email);

    public void notifyAdminsEmailDeleted(Email email);
}
