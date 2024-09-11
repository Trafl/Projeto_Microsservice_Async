package com.empiricus.service_usuario.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UsuarioUpdateDTOInput {

    private String nome;

    private String cpf;


}
