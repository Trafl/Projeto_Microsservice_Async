package com.empiricus.service_email.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTOOutput {

    private Long id;

    private Long usuario_id;

    private String email;

    private LocalDate data_criacao;

}
