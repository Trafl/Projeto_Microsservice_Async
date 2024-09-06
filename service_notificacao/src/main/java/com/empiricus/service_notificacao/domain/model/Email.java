package com.empiricus.service_notificacao.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class Email {

    private Long usuario_id;

    private String email;

    private LocalDate data_criacao;

}
