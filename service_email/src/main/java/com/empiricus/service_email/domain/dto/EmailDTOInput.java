package com.empiricus.service_email.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTOInput {

    @NotNull
    private Long usuario_id;

    @NotBlank
    private String email;

}
