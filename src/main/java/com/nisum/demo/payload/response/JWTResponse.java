package com.nisum.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTResponse {
    private String token;
    private String type = "Bearer";


    public JWTResponse(String accessToken) {
        this.token = accessToken;

    }

}
