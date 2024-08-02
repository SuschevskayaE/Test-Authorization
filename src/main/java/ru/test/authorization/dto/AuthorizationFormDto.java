package ru.test.authorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationFormDto {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String password;

    @NotNull
    @Email
    @Size(min = 1, max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
}
