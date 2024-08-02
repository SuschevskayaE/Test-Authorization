package ru.test.authorization.service;

import java.util.concurrent.TimeoutException;

public interface MailSender {

    void send(String emailTo, String subject, String message) throws TimeoutException;
}
