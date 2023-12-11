package com.oguzhan.spring.security.dtos;

public record SignUpDto(String firstName, String lastName, String login, char[] password) {
}
