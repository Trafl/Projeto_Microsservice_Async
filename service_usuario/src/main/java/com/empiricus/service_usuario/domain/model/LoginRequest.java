package com.empiricus.service_usuario.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String username;

    private String password;
}
