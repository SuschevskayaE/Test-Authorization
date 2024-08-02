package ru.test.authorization.service;

import ru.test.authorization.dto.Message;
import ru.test.authorization.dto.MessageId;

import java.util.concurrent.TimeoutException;

public interface MessagingService {

    /**
     * Отправка сообщения в шину.
     *
     * @param msg сообщение для отправки.
     * @return идентификатор отправленного сообщения (correlationId)
     */
    MessageId send(Message msg);

    /**
     * Встает на ожидание ответа по сообщению с messageId.
     * <p>
     * Редко, но может кинуть исключение по таймауту.
     *
     * @param messageId идентификатор сообщения, на которое ждем ответ.
     * @return Тело ответа.
     */
    Message receive(MessageId messageId, Message messageType) throws TimeoutException;
}
