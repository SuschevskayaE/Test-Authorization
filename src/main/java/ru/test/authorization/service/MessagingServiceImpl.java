package ru.test.authorization.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.test.authorization.dto.Message;
import ru.test.authorization.dto.MessageId;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MessagingServiceImpl implements MessagingService {

    @Override
    public MessageId send(Message msg) {
        return new MessageId(UUID.randomUUID());
    }

    @Override
    public Message receive(MessageId messageId, Message messageType) throws TimeoutException {
        if (shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        return new Message(messageType.getLogin(), messageType.getEmail(), new Random().nextBoolean());
    }


    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}
