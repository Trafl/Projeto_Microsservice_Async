package com.empiricus.service_notificacao.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
public class Email {

    private Long usuario_id;

    private String email;

}
