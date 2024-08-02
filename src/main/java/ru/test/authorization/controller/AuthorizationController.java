package ru.test.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.test.authorization.dto.AuthorizationFormDto;
import ru.test.authorization.dto.AuthorizationFormResponseDto;
import ru.test.authorization.service.AuthorizationService;

@RestController
@RequestMapping(path = "/authorization", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AuthorizationController {

    private final AuthorizationService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Авторизация")
    public AuthorizationFormResponseDto authorization(@Valid @RequestBody AuthorizationFormDto authorizationDto) {
        return service.authorization(authorizationDto);
    }
}
