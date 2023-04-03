package com.nisum.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserResponseDTO {
    Long id;
    LocalDateTime created;
    LocalDateTime modified;

    @Nullable
    LocalDateTime lastLogin;
    boolean isActive;
}
