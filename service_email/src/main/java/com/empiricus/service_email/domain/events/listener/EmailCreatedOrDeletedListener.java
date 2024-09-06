package com.empiricus.service_email.domain.events.listener;

import com.empiricus.service_email.domain.events.event.EmailCreated;
import com.empiricus.service_email.domain.events.event.EmailDeleted;
import com.empiricus.service_email.domain.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailCreatedOrDeletedListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void eventNotificationCreated(EmailCreated event){
        log.info("[{}] - [EmailCreatedOrDeletedListener] - disparando evento EmailCreated", LocalDateTime.now());
        notificationService.notifyAdminsEmailCreated(event.getEmail());
    }

    @Async
    @EventListener
    public void eventNotificationDeleted(EmailDeleted event){
        log.info("[{}] - [EmailCreatedOrDeletedListener] - disparando evento EmailDeleted", LocalDateTime.now());
        notificationService.notifyAdminsEmailDeleted(event.getEmail());
    }
}
