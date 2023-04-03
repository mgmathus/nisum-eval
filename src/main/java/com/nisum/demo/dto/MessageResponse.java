package com.nisum.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    String message;
    UserResponseDTO data;

    public MessageResponse(String message, UserResponseDTO data) {
        this.message = message;
        this.data = data;
    }
}