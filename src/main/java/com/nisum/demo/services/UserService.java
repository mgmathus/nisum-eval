package com.nisum.demo.services;

import com.nisum.demo.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(final UserDTO userinfo);
    boolean checkEmail(final String email);
    void updateLastLogin(final String email);
    UserDTO getUser(final Long id) throws Exception;
}
