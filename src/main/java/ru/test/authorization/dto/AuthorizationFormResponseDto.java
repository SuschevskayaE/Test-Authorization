package ru.test.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizationFormResponseDto {

    private Long id;

    private String login;

    private String email;

    private String name;

    private boolean approval;
}
