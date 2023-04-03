package com.nisum.demo.controllers;

import com.nisum.demo.dto.ImmutableUserDTO;
import com.nisum.demo.dto.MessageResponse;
import com.nisum.demo.dto.UserDTO;
import com.nisum.demo.dto.UserResponseDTO;
import com.nisum.demo.payload.request.SignupRequest;
import com.nisum.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {
        if (userService.checkEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El Correo ya esta registrado", null));
        }

        try {
            final UserDTO registeredUser = userService.registerUser(ImmutableUserDTO.builder()
                    .id(0L)
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .addAllPhones(signUpRequest.getPhones())
                    .isActive(true)
                    .modified(LocalDateTime.now())
                    .build());

            final UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(registeredUser.id());
            userResponseDTO.setCreated(registeredUser.created());
            userResponseDTO.setModified(registeredUser.modified());
            userResponseDTO.setLastLogin(registeredUser.lastLogin());
            userResponseDTO.setActive(registeredUser.isActive());

            final MessageResponse messageResponse = new MessageResponse("Usuario Registrado con exito", userResponseDTO);

            return ResponseEntity.ok(messageResponse);

        } catch (final IllegalArgumentException ex){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(ex.getMessage(), null));
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MessageResponse> getUser(@Valid @PathVariable final Long id) {
        try {
            UserDTO user = userService.getUser(id);

            final UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(user.id());
            userResponseDTO.setCreated(user.created());
            userResponseDTO.setModified(user.modified());
            userResponseDTO.setLastLogin(user.lastLogin());
            userResponseDTO.setActive(user.isActive());

            final MessageResponse messageResponse = new MessageResponse("Usuario Registrado con exito", userResponseDTO);

            return ResponseEntity.ok(messageResponse);
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error obteniendo informacin del usuario, " + ex.getMessage(), null));
        }
    }
}