package com.oguzhan.spring.security.controllers;

import com.oguzhan.spring.security.config.UserAuthProvider;
import com.oguzhan.spring.security.dtos.CredentialsDto;
import com.oguzhan.spring.security.dtos.SignUpDto;
import com.oguzhan.spring.security.dtos.UserDto;
import com.oguzhan.spring.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.register(signUpDto);

        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }

}
