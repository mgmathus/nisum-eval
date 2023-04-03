package com.nisum.demo.services;


import com.nisum.demo.dto.ImmutableUserDTO;
import com.nisum.demo.dto.UserDTO;
import com.nisum.demo.models.User;
import com.nisum.demo.repository.PhoneRepository;
import com.nisum.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PhoneRepository phoneRepository;

    @Test
    void registerUserTest() {

        final User user = new User();
        user.setId(10L);
        user.setName("Test");
        user.setPassword("***");
        user.setEmail("test@test.com");
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setPhones(Collections.EMPTY_SET);

        Mockito.when(userRepository.saveAndFlush(any())).thenReturn(user);
        Mockito.when(phoneRepository.saveAllAndFlush(any())).thenReturn(Collections.EMPTY_LIST);

        final UserDTO userDTO = ImmutableUserDTO.builder()
                .id(0L)
                .name("Test")
                .email("test@test.com")
                .password("123456aA")
                .addAllPhones(Collections.EMPTY_SET)
                .isActive(true)
                .modified(LocalDateTime.now())
                .build();

        final UserDTO savedUser = userService.registerUser(userDTO);
        Assertions.assertNotNull(savedUser);
    }
}
