package com.empiricus.service_usuario.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class UsuarioDTOOutput {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate data_criacao;

    private LocalDate data_atualizacao;

    private Boolean eh_admin;
}
