package com.empiricus.service_gateway.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token){
        this.token = token;
    }
}
