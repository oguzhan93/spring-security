package com.oguzhan.spring.security.services;

import com.oguzhan.spring.security.dtos.CredentialsDto;
import com.oguzhan.spring.security.dtos.SignUpDto;
import com.oguzhan.spring.security.dtos.UserDto;
import com.oguzhan.spring.security.entities.User;
import com.oguzhan.spring.security.exceptions.AppException;
import com.oguzhan.spring.security.mappers.UserMapper;
import com.oguzhan.spring.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
       User user = userRepository.findByLogin(credentialsDto.login()).orElseThrow(() -> new AppException("User Not Found!", HttpStatus.NOT_FOUND));
       if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
           return userMapper.toUserDto(user);
       }

       throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> optionalUser = userRepository.findByLogin(signUpDto.login());
        if (optionalUser.isPresent()) {
            throw new AppException("That user is already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);

    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new AppException("Unknown User!", HttpStatus.NOT_FOUND));
    }

}
