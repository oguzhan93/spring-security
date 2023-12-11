package com.oguzhan.spring.security.mappers;

import com.oguzhan.spring.security.dtos.SignUpDto;
import com.oguzhan.spring.security.dtos.UserDto;
import com.oguzhan.spring.security.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    //password ignored because frontend sends the password as a string, we keep the password as a char array.
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
