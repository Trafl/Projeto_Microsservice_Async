package com.empiricus.service_email.domain.events.event;

import com.empiricus.service_email.domain.model.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailCreated {

    private Email email;
}
