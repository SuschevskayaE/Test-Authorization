package ru.test.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test.authorization.dto.AuthorizationFormDto;
import ru.test.authorization.dto.AuthorizationFormResponseDto;
import ru.test.authorization.dto.Message;
import ru.test.authorization.dto.MessageId;
import ru.test.authorization.entity.AuthorizationFormEntity;
import ru.test.authorization.exception.GatewayTimeoutException;
import ru.test.authorization.mapper.AuthorizationFormMapper;
import ru.test.authorization.repository.AuthorizationFormRepository;

import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationFormRepository repository;
    private final AuthorizationFormMapper mapper;

    private final MailSender mailSender;
    private final MessagingService messagingService;

    @Override
    public AuthorizationFormResponseDto authorization(AuthorizationFormDto dto) {
        AuthorizationFormEntity entity = repository.save(mapper.toEntity(dto));

        Message message = new Message(entity.getLogin(), entity.getEmail(), false);

        MessageId messageId = messagingService.send(message);

        try {
            Message response = messagingService.receive(messageId, message);

            entity.setApproval(response.isApproval());
            repository.save(entity);

            String messageEmail = String.format("" +
                            "Добрый день, %s! \n" +
                            "Ваша почта %s",
                    entity.getName(),
                    (entity.isApproval() ? "подтверждена" : "не подтверждена"));
            mailSender.send(entity.getEmail(), "Уведомление об авторизации", messageEmail);
        } catch (TimeoutException exception) {
            throw new GatewayTimeoutException("Ожидание ответа от почтового сервиса");
        }
        return mapper.toAuthorizationFormDto(entity);
    }
}
