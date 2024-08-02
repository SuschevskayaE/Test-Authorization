package ru.test.authorization.service;

import ru.test.authorization.dto.AuthorizationFormDto;
import ru.test.authorization.dto.AuthorizationFormResponseDto;

public interface AuthorizationService {
    AuthorizationFormResponseDto authorization(AuthorizationFormDto dto);
}
