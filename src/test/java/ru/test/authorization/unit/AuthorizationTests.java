package ru.test.authorization.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.authorization.dto.AuthorizationFormDto;
import ru.test.authorization.dto.AuthorizationFormResponseDto;
import ru.test.authorization.dto.Message;
import ru.test.authorization.dto.MessageId;
import ru.test.authorization.entity.AuthorizationFormEntity;
import ru.test.authorization.exception.GatewayTimeoutException;
import ru.test.authorization.mapper.AuthorizationFormMapper;
import ru.test.authorization.repository.AuthorizationFormRepository;
import ru.test.authorization.service.AuthorizationServiceImpl;
import ru.test.authorization.service.MailSender;
import ru.test.authorization.service.MessagingService;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationTests {

    @InjectMocks
    AuthorizationServiceImpl service;

    @Mock
    private AuthorizationFormRepository repository;

    @Mock
    private AuthorizationFormMapper mapper;

    @Mock
    private MailSender mailSender;

    @Mock
    private MessagingService messagingService;

    @Test
    void authorizationSuccess() throws TimeoutException {
        AuthorizationFormDto authorizationFormDto = new AuthorizationFormDto("Login", "password", "Kitty25012@yandex.ru", "Super Name");

        AuthorizationFormEntity entity = new AuthorizationFormEntity();
        entity.setId(new Random().nextLong());
        entity.setLogin(authorizationFormDto.getLogin());
        entity.setPassword(authorizationFormDto.getPassword());
        entity.setEmail(authorizationFormDto.getEmail());
        entity.setName(authorizationFormDto.getName());
        entity.setApproval(true);

        when(repository.save(any())).thenReturn(entity);
        when(messagingService.send(any())).thenReturn(new MessageId(UUID.randomUUID()));
        when(messagingService.receive(any(), any())).thenReturn(new Message(authorizationFormDto.getLogin(),
                authorizationFormDto.getEmail(), true));
        when(mapper.toAuthorizationFormDto(any())).thenReturn(new AuthorizationFormResponseDto(new Random().nextLong(),
                authorizationFormDto.getLogin(), authorizationFormDto.getEmail(), authorizationFormDto.getName(), true));

        var result = service.authorization(authorizationFormDto);

        assertNotNull(result);
        assertEquals(authorizationFormDto.getLogin(), result.getLogin());
        assertEquals(authorizationFormDto.getEmail(), result.getEmail());
        assertEquals(authorizationFormDto.getName(), result.getName());
    }

    @Test
    void authorizationMessagingTimeoutException() throws TimeoutException {
        mapper = Mappers.getMapper(AuthorizationFormMapper.class);
        AuthorizationFormDto authorizationFormDto = new AuthorizationFormDto("Login", "password", "Kitty25012@yandex.ru", "Super Name");

        when(repository.save(any())).thenReturn(mapper.toEntity(authorizationFormDto));
        when(messagingService.send(any())).thenReturn(new MessageId(UUID.randomUUID()));
        when(messagingService.receive(any(), any())).thenThrow(new TimeoutException());

        final GatewayTimeoutException exception = assertThrows(
                GatewayTimeoutException.class,
                () -> service.authorization(authorizationFormDto)
        );

        assertEquals("Ожидание ответа от почтового сервиса", exception.getMessage());
    }

    @Test
    void authorizationMailTimeoutException() throws TimeoutException {
        mapper = Mappers.getMapper(AuthorizationFormMapper.class);
        AuthorizationFormDto authorizationFormDto = new AuthorizationFormDto("Login", "password", "Kitty25012@yandex.ru", "Super Name");

        when(repository.save(any())).thenReturn(mapper.toEntity(authorizationFormDto));
        when(messagingService.send(any())).thenReturn(new MessageId(UUID.randomUUID()));
        when(messagingService.receive(any(), any())).thenReturn(new Message(authorizationFormDto.getLogin(),
                authorizationFormDto.getEmail(), true));
        doThrow(new TimeoutException()).when(mailSender).send(anyString(), anyString(), anyString());

        final GatewayTimeoutException exception = assertThrows(
                GatewayTimeoutException.class,
                () -> service.authorization(authorizationFormDto)
        );

        assertEquals("Ожидание ответа от почтового сервиса", exception.getMessage());
    }

}
