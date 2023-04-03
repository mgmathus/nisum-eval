package com.nisum.demo.dto;

import com.nisum.demo.models.Phone;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Set;

@Value.Immutable
public interface UserDTO {
    Long id();
    String name();
    String email();

    @Nullable
    String password();

    boolean isActive();

    @Nullable
    LocalDateTime created();

    @Nullable
    LocalDateTime modified();

    @Nullable
    LocalDateTime lastLogin();
    Set<Phone> phones();
}
