package com.empiricus.service_usuario.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UsuarioDTOInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank
    private String password;

    @NotNull
    private Boolean eh_admin;
}
