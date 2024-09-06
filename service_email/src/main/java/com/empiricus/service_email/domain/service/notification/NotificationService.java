package com.empiricus.service_email.domain.service.notification;

import com.empiricus.service_email.domain.model.Email;

public interface NotificationService {

    public void notifyAdminsEmailCreated(Email email);

    public void notifyAdminsEmailDeleted(Email email);
}
