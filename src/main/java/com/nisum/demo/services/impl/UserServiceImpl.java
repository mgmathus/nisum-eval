package com.nisum.demo.services.impl;

import com.nisum.demo.dto.ImmutableUserDTO;
import com.nisum.demo.dto.UserDTO;
import com.nisum.demo.models.Phone;
import com.nisum.demo.models.User;
import com.nisum.demo.repository.PhoneRepository;
import com.nisum.demo.repository.UserRepository;
import com.nisum.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${password.validation.regex}")
    String passwordRegex;

    public UserDTO registerUser(@NotNull final UserDTO userinfo) {
        final User user = new User();

        if(!userinfo.password().matches(passwordRegex)){
            throw new IllegalArgumentException("Password no valido, debe tener al menos"
                    + " una letra minuscula,"
                    + " una letra mayuscula,"
                    + " una un digito entre 0 y 9,"
                    + " y tener una longitud mayor a 6 caracteres");
        }

            user.setName(userinfo.name());
            user.setEmail(userinfo.email());
            user.setPassword(encoder.encode(userinfo.password()));
            user.setActive(userinfo.isActive());

            final User savedUser = userRepository.saveAndFlush(user);

            userinfo.phones().forEach(f -> f.setUserId(savedUser.getId()));
            List<Phone> userPhone = phoneRepository.saveAllAndFlush(userinfo.phones());

            return ImmutableUserDTO.builder()
                    .id(savedUser.getId())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .isActive(true)
                    .addAllPhones(userPhone)
                    .created(savedUser.getCreated())
                    .modified(savedUser.getModified())
                    .isActive(savedUser.isActive())
                    .build();

    }

    public boolean checkEmail(@NotNull final String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateLastLogin(@NotNull final String email){
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(current -> {
            current.setLastLogin(LocalDateTime.now());
            userRepository.saveAndFlush(current);
        });
    }

    public UserDTO getUser(@NotNull final Long id) throws Exception {
        final User user = userRepository.getById(id);

        if (user != null) {
            return ImmutableUserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .isActive(user.isActive())
                    .addAllPhones(Collections.EMPTY_SET)
                    .created(user.getCreated())
                    .modified(user.getModified())
                    .isActive(user.isActive())
                    .lastLogin(user.getLastLogin())
                    .build();
        } else {
            throw new Exception("User id not found");
        }
    }

}